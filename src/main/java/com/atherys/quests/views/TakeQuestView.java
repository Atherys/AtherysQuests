package com.atherys.quests.views;

import com.atherys.core.utils.Question;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.function.Consumer;

public class TakeQuestView implements QuestView<Quest> {

    private final Quest<?> quest;

    private Consumer<Player> onAccept;

    public TakeQuestView(Quest<?> quest) {
        this.quest = quest;
    }

    public Quest<?> getQuest() {
        return quest;
    }

    public BookView toBookView(Player player) {
        BookView.Builder questView = BookView.builder();

        Text.Builder intro = Text.builder();
        intro.append(Text.of(quest.getName(), "\n\n"));
        intro.append(quest.getDescription());

        questView.addPage(intro.build());

        QuestView view = quest.createView();

        questView.addPage(view.getFormattedObjectives());

        questView.addPage(view.getFormattedRewards());

        Text.Builder takeQuest = Text.builder();
        Question question = Question.of(Text.of("Do you accept the quest \"", quest.getName(), "\"?"))
                .addAnswer(Question.Answer.of(Text.of(TextStyles.BOLD, TextColors.DARK_GREEN, "Yes"), src -> {
                    if (AtherysQuests.getInstance().getQuesterFacade().pickupQuest(src, quest)) {
                        if (onAccept != null) onAccept.accept(player);
                    }
                }))
                .addAnswer(Question.Answer.of(Text.of(TextStyles.BOLD, TextColors.DARK_RED, "No"), src -> {
                    AtherysQuests.getInstance().getQuestMessagingService().error(src, "You have declined the quest \"", quest.getName(), "\".");
                }))
                .build();

        question.register();

        takeQuest.append(question.asText());

        questView.addPage(takeQuest.build());
        return questView.build();
    }

    @Override
    public void show(Player viewer) {
        viewer.sendBookView(toBookView(viewer));
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

    public void setOnAccept(Consumer<Player> onAccept) {
        this.onAccept = onAccept;
    }
}
