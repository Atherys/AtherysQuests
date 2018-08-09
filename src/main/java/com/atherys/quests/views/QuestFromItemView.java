package com.atherys.quests.views;

import com.atherys.core.utils.Question;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.util.QuestMsg;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class QuestFromItemView extends TakeQuestView{


    public QuestFromItemView(Quest<?> quest) {
        super(quest);
    }

    @Override
    public void show(Player viewer) {
        BookView.Builder questView = BookView.builder();

        Text.Builder intro = Text.builder();
        intro.append(Text.of(getQuest().getName(), "\n\n"));
        intro.append(getQuest().getDescription());

        questView.addPage(intro.build());

        QuestView view = getQuest().createView();

        questView.addPage(view.getFormattedObjectives());

        questView.addPage(view.getFormattedRewards());

        Text.Builder takeQuest = Text.builder();
        Question question = Question.of(Text.of("Do you accept the quest \"", getQuest().getName(), "\"?"))
                .addAnswer(Question.Answer.of(Text.of(TextStyles.BOLD, TextColors.DARK_GREEN, "Yes"), player -> {
                    if(AtherysQuests.getQuesterManager().getQuester(player).pickupQuest(getQuest())){
                        player.setItemInHand(HandTypes.MAIN_HAND, ItemStack.builder().itemType(ItemTypes.AIR).build());
                    }
                }))
                .addAnswer(Question.Answer.of(Text.of(TextStyles.BOLD, TextColors.DARK_RED, "No"), player -> {
                    QuestMsg.error(player, "You have declined the quest \"", getQuest().getName(), "\".");
                }))
                .build();

        question.register();

        takeQuest.append(question.asText());

        questView.addPage(takeQuest.build());

        viewer.sendBookView(questView.build());
    }
}
