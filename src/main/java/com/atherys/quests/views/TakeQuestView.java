package com.atherys.quests.views;

import com.atherys.core.utils.Question;
import com.atherys.core.views.View;
import com.atherys.quests.managers.QuesterManager;
import com.atherys.quests.quest.Quest;
import com.atherys.quests.quest.QuestMsg;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class TakeQuestView implements View<Quest> {

    private final Quest quest;

    public TakeQuestView( Quest quest ) {
        this.quest = quest;
    }

    @Override
    public void show( Player viewer ) {
        BookView.Builder questView = BookView.builder();

        Text.Builder intro = Text.builder();
        intro.append( Text.of( quest.getName(), "\n\n" ) );
        intro.append( quest.getDescription() );

        questView.addPage( intro.build() );

        Text.Builder objectives = Text.builder();
        objectives.append( Text.of( "Objectives:\n" ) );
        quest.getObjectives().forEach( objective -> {
            objectives.append( Text.of( objective.isComplete() ? TextStyles.NONE : TextStyles.STRIKETHROUGH, objective.toText(), "\n" ) );
        } );

        questView.addPage( objectives.build() );

        Text.Builder rewards = Text.builder();
        objectives.append( Text.of( "Rewards:\n" ) );
        quest.getRewards().forEach( reward -> {
            rewards.append( Text.of( reward.toText(), "\n" ) );
        } );

        questView.addPage( rewards.build() );

        Text.Builder takeQuest = Text.builder();
        Question question = Question.of( Text.of( "Do you accept this quest?" ) )
                .addAnswer( Question.Answer.of( Text.of( TextStyles.BOLD, TextColors.DARK_GREEN, "[Accept]" ), player -> {
                    QuesterManager.getInstance().getQuester( player ).pickupQuest( quest );
                } ) )
                .addAnswer( Question.Answer.of( Text.of( TextStyles.BOLD, TextColors.DARK_RED, "[Decline]" ), player -> {
                    QuestMsg.error( player, "You have declined the quest \"", quest.getName(), "\"." );
                } ) )
                .build();
        takeQuest.append( question.asText() );

        questView.addPage( takeQuest.build() );

        viewer.sendBookView( questView.build() );
    }

}
