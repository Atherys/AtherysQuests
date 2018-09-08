package com.atherys.quests.views;

import com.atherys.core.utils.Question;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.util.QuestMsg;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class TakeQuestView implements QuestView<Quest> {

    private final Quest<?> quest;

    public TakeQuestView(Quest<?> quest) {
        this.quest = quest;
    }

    public Quest<?> getQuest(){
        return quest;
    }

    @Override
    public void show(Player viewer) {
        BookView.Builder questView = BookView.builder();

        Text.Builder intro = Text.builder();
        intro.append(Text.of(quest.getName(), "\n\n"));
        intro.append(quest.getDescription());

        questView.addPage(intro.build());

        QuestView view = quest.createView();

        questView.addPage(view.getFormattedObjectives());

        questView.addPage(view.getFormattedRewards());

        Text.Builder takeQuest = Text.builder();
        Question question = Question.of(Text.of("Do you accept the completedQuest \"", quest.getName(), "\"?"))
                .addAnswer(Question.Answer.of(Text.of(TextStyles.BOLD, TextColors.DARK_GREEN, "Yes"), player -> {
                    AtherysQuests.getQuesterManager().getQuester(player).pickupQuest(quest);
                }))
                .addAnswer(Question.Answer.of(Text.of(TextStyles.BOLD, TextColors.DARK_RED, "No"), player -> {
                    QuestMsg.error(player, "You have declined the completedQuest \"", quest.getName(), "\".");
                }))
                .build();

        question.register();

        takeQuest.append(question.asText());

        questView.addPage(takeQuest.build());

        viewer.sendBookView(questView.build());
    }

    @Override
    public Text getFormattedRequirements() {
        Text.Builder reqText = Text.builder();
        reqText.append(Text.of(QuestMsg.MSG_PREFIX, " Quest Requirements: "));
        quest.getRequirements().forEach(requirement -> reqText.append(Text.of(QuestMsg.MSG_PREFIX, " * ", requirement.toText(), Text.NEW_LINE)));
        return reqText.build();
    }

    @Override
    public Text getFormattedObjectives() {
        Text.Builder objectives = Text.builder();
        objectives.append(Text.of("Objectives:\n"));
        quest.getObjectives().forEach(objective -> {
            objectives.append(Text.of(!objective.isComplete() ? TextStyles.NONE : TextStyles.STRIKETHROUGH, objective.toText(), TextStyles.NONE, Text.NEW_LINE));
        });

        return objectives.build();
    }

    @Override
    public Text getFormattedRewards() {
        Text.Builder rewards = Text.builder();
        rewards.append(Text.of("Rewards:\n"));
        quest.getRewards().forEach(reward -> {
            rewards.append(Text.of(reward.toText(), "\n"));
        });

        return rewards.build();
    }
}
