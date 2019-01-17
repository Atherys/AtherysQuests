package com.atherys.quests.facade;

import com.atherys.core.utils.Question;
import com.atherys.quests.api.exception.QuestCommandExceptions;
import com.atherys.quests.api.exception.QuestRequirementsException;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.service.QuestLocationService;
import com.atherys.quests.service.QuestMessagingService;
import com.atherys.quests.service.QuestService;
import com.atherys.quests.service.QuesterService;
import com.atherys.quests.views.QuestLog;
import com.atherys.quests.views.TakeQuestView;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

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
        if (fromLocation.equals(toLocation)) return;

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

    public void showQuestLog(Player player) throws CommandException {
        Quester quester = questerService.getQuester(player);

        QuestLog questLog = new QuestLog(quester);
        questLog.show(player);
    }

    public void removeQuestFromPlayer(Player player, String questId) throws CommandException {
        Quester quester = questerService.getQuester(player);
        Optional<Quest> quest = questService.getQuest(questId);

        if (!quest.isPresent()) {
            throw QuestCommandExceptions.invalidQuestId();
        }

        questerService.removeQuest(quester, quest.get());
    }
}
