package com.atherys.quests.facade;

import com.atherys.core.utils.Question;
import com.atherys.quests.api.exception.QuestCommandExceptions;
import com.atherys.quests.api.exception.QuestRequirementsException;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.entity.SimpleQuester;
import com.atherys.quests.service.*;
import com.atherys.quests.views.TakeQuestView;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class QuesterFacade {

    @Inject
    QuesterService questerService;

    @Inject
    QuestService questService;

    @Inject
    QuestLocationService questLocationService;

    @Inject
    QuestMessagingService questMsg;

    @Inject
    TimedQuestService timedQuestService;

    QuesterFacade() {
    }

    public <T extends Quest> boolean pickupQuest(Player player, Quest<T> quest) {

        Quester quester = questerService.getQuester(player);

        try {
            boolean result = questerService.pickupQuest(quester, quest);

            if (result) {
                questMsg.info(quester, "You have started the quest \"", quest.getName(), "\"");
            } else {
                questMsg.error(quester, "You are either already doing this quest, or have done it before in the past.");
            }

            return result;
        } catch (QuestRequirementsException e) {
            Text.Builder reqText = Text.builder();
            reqText.append(Text.of(QuestMessagingService.MSG_PREFIX, " You do not meet the requirements for this quest."));
            reqText.append(quest.createView().getFormattedRequirements());

            questMsg.noformat(quester, reqText.build());

            return false;
        }
    }

    public <T extends Quest> boolean turnInQuest(Player player, Quest<T> quest) {
        Quester quester = questerService.getQuester(player);

        boolean result = questerService.turnInQuest(quester, quest);

        if (result) questMsg.info(quester, "You have turned in the quest \"", quest.getName(), "\"");

        return result;
    }

    public void onPlayerMoveToQuestRadius(Location<World> fromLocation, Location<World> toLocation, Player player) {
        if (questLocationService.getByRadius(fromLocation).isPresent()) return;

        questLocationService.getByRadius(toLocation).ifPresent(questLocation -> {
            Quest quest = questService.getQuest(questLocation.getQuestId()).get();

            if (questerService.getQuester(player).hasQuest(quest)) return;

            Question question = Question.of(Text.of("You have found the quest \"", quest.getName(), "\", would you like to take it?"))
                    .addAnswer(Question.Answer.of(Text.of(TextStyles.BOLD, TextColors.DARK_GREEN, "Yes"), quester -> {
                        if (questLocationService.checkContain(questLocation, player.getLocation())) {
                            new TakeQuestView(quest).show(quester);
                        } else {
                            questMsg.error(quester, "You have left the quest area.");
                        }
                    }))
                    .addAnswer(Question.Answer.of(Text.of(TextStyles.BOLD, TextColors.DARK_RED, "No"), quester -> {
                        questMsg.error(quester, "You have declined the quest \"", quest.getName(), "\".");
                    }))
                    .build();

            question.pollChat(player);
        });
    }

    public void showQuestLog(Player player) {
        Quester quester = questerService.getQuester(player);

        BookView.Builder log = BookView.builder();

        List<Text> pages = new ArrayList<>();
        Text.Builder lastPage = Text.builder();

        lastPage.append(Text.of("Quest Log:\n"));

        int i = 1;
        for (Quest quest : quester.getOngoingQuests()) {
            Text.Builder questView = Text.builder();
            questView.append(Text.of("[", i, "] "));
            if (quest.isComplete()) {
                questView.append(Text.of(TextStyles.STRIKETHROUGH, quest.getName(), TextStyles.NONE));
            } else if (quest.isFailed()) {
                questView.append(Text.of(TextColors.RED, TextStyles.STRIKETHROUGH, quest.getName(), TextStyles.RESET));
            } else {
                questView.append(Text.of(quest.getName()));
            }
            questView.onHover(TextActions.showText(Text.of("Click to view more details.")));
            questView.onClick(TextActions.executeCallback(src -> quest.createView().show(player)));

            if (i % 7 == 0) {
                pages.add(lastPage.build());
                lastPage = Text.builder();
            } else {
                lastPage.append(Text.of(questView.build(), "\n"));
            }

            i++;
        }

        pages.add(lastPage.build());

        log.addPages(pages);

        player.sendBookView(log.build());
    }

    public void showQuestFromItem() {

    }

    public void removeQuestFromPlayer(Player player, String questId) throws CommandException {
        Quester quester = questerService.getQuester(player);
        Optional<Quest> quest = questService.getQuest(questId);

        if (!quest.isPresent()) {
            throw QuestCommandExceptions.invalidQuestId();
        }

        questerService.removeQuest(quester, quest.get());
    }

    public boolean validatePlayerObject(Player player) {
        if (player == null) {
            throw new RuntimeException("Player object must not be null to validate");
        }

        if (!player.isOnline()) {
            throw new RuntimeException("Player must be online to validate");
        }

        return !player.isRemoved() && player.isLoaded();
    }

    public void notifyAndUpdateCachedPlayer(Event event, Player player) {
        // If the player object is invalid, get a new Player reference from the server and re-call the method
        if (!validatePlayerObject(player)) {
            player = Sponge.getServer().getPlayer(player.getUniqueId()).orElse(null);
            notifyAndUpdateCachedPlayer(event, player);
        }


        SimpleQuester quester = questerService.getQuester(player);
        quester.setCachedPlayer(player);

        questerService.notify(event, quester);
    }

    public void updateCachedPlayer(Player player) {
        // If the player object is invalid, get a new Player reference from the server and re-call the method
        if (!validatePlayerObject(player)) {
            player = Sponge.getServer().getPlayer(player.getUniqueId()).orElse(null);
            updateCachedPlayer(player);
        }


        SimpleQuester quester = questerService.getQuester(player);
        quester.setCachedPlayer(player);
    }

    public void onCompleteTimedQuest(Quester quester, Quest quest) {
        timedQuestService.stopTimingQuest(quest, quester);
    }

    public void onStartTimedQuest(Quester quester, Quest<?> quest) {
        quest.getTimedComponent().get().startTiming();
        timedQuestService.startTimingQuest(quest, quester);
    }
}
