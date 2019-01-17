package com.atherys.quests.views;

import com.atherys.core.utils.Question;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.service.QuestMessagingService;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class AnyQuestView<T extends Quest> implements QuestView<Quest<T>> {

    protected final Quest<T> quest;

    public AnyQuestView(Quest<T> quest) {
        this.quest = quest;
    }

    public void show(Player player) {
        player.sendBookView(toBookView(player));
    }

    public BookView toBookView(Player player) {
        BookView.Builder questView = BookView.builder();

        Text.Builder intro = Text.builder();
        intro.append(Text.of(quest.getName(), "\n\n"));
        intro.append(quest.getDescription());

        questView.addPage(intro.build());

        questView.addPage(getFormattedObjectives());

        questView.addPage(getFormattedRewards());

        if (quest.isComplete()) {
            Question completeQuest = Question.of(Text.of("You have completed this quest. Would you like to turn it in?"))
                    .addAnswer(Question.Answer.of(Text.of(TextStyles.BOLD, TextColors.DARK_GREEN, "Turn In"), (src) -> {
                        AtherysQuests.getInstance().getQuesterFacade().turnInQuest(player, quest);
                    }))
                    .build();

            completeQuest.register();

            Text completeQuestPage = Text.builder()
                    .append(completeQuest.asText())
                    .build();

            questView.addPage(completeQuestPage);
        }

        return questView.build();
    }

    @Override
    public Text getFormattedRequirements() {
        Text.Builder reqText = Text.builder();
        reqText.append(Text.of(QuestMessagingService.MSG_PREFIX, " Quest Requirements: "));
        quest.getRequirements().forEach(requirement -> reqText.append(Text.NEW_LINE, Text.of(QuestMessagingService.MSG_PREFIX, " * ", requirement.toText())));
        return reqText.build();
    }

    @Override
    public Text getFormattedObjectives() {
        Text.Builder objectives = Text.builder();
        objectives.append(Text.of("Objectives:\n"));
        quest.getObjectives().forEach(objective -> {
            objectives.append(Text.of(!objective.isComplete() ? TextStyles.NONE : TextStyles.STRIKETHROUGH, objective.toText(), TextStyles.RESET, Text.NEW_LINE));
        });

        return objectives.build();
    }

    @Override
    public Text getFormattedRewards() {
        Text.Builder rewards = Text.builder();
        rewards.append(Text.of("Rewards:\n"));
        quest.getRewards().forEach(reward -> {
            rewards.append(Text.of(reward.toText(), Text.NEW_LINE));
        });
        return rewards.build();
    }
}
