package com.atherys.quests.views;

import com.atherys.quests.quest.QuestMsg;
import com.atherys.quests.quest.SimpleQuest;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextStyles;

public class SimpleQuestView implements QuestView<SimpleQuest> {

    private final SimpleQuest quest;

    public SimpleQuestView( SimpleQuest quest ) {
        this.quest = quest;
    }

    @Override
    public void show( Player player ) {
        player.sendBookView( toBookView() );
    }

    public BookView toBookView() {
        BookView.Builder questView = BookView.builder();

        Text.Builder intro = Text.builder();
        intro.append( Text.of( quest.getName(), "\n\n" ) );
        intro.append( quest.getDescription() );

        questView.addPage( intro.build() );

        questView.addPage( getFormattedObjectives() );

        questView.addPage( getFormattedRewards() );

        return questView.build();
    }

    @Override
    public Text getFormattedRequirements() {
        Text.Builder reqText = Text.builder();
        reqText.append( Text.of( QuestMsg.MSG_PREFIX, " Quest Requirements: " ) );
        quest.getRequirements().forEach( requirement -> reqText.append( Text.of( QuestMsg.MSG_PREFIX, " * ", requirement.toText(), Text.NEW_LINE ) ) );
        return reqText.build();
    }

    @Override
    public Text getFormattedObjectives () {
        Text.Builder objectives = Text.builder();
        objectives.append( Text.of( "Objectives:\n" ) );
        quest.getObjectives().forEach( objective -> {
            objectives.append( Text.of( !objective.isComplete() ? TextStyles.NONE : TextStyles.STRIKETHROUGH, objective.toText(), TextStyles.NONE, Text.NEW_LINE ) );
        } );

        return objectives.build();
    }

    @Override
    public Text getFormattedRewards () {
        Text.Builder rewards = Text.builder();
        rewards.append( Text.of( "Rewards:\n" ) );
        quest.getRewards().forEach( reward -> {
            rewards.append( Text.of( reward.toText(), "\n" ) );
        } );

        return rewards.build();
    }
}
