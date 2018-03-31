package com.atherys.quests.views;

import com.atherys.quests.quest.Quest;
import com.atherys.quests.quest.QuestMsg;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextStyles;

public class AnyQuestView<T extends Quest> implements QuestView<Quest<T>> {

    private final Quest<T> quest;

    public AnyQuestView ( Quest<T> quest ) {
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
        quest.getRequirements().forEach( requirement -> reqText.append( Text.NEW_LINE, Text.of( QuestMsg.MSG_PREFIX, " * ", requirement.toText() ) ) );
        return reqText.build();
    }

    @Override
    public Text getFormattedObjectives () {
        Text.Builder objectives = Text.builder();
        objectives.append( Text.of( "Objectives:\n" ) );
        quest.getObjectives().forEach( objective -> {
            objectives.append( Text.of( !objective.isComplete() ? TextStyles.NONE : TextStyles.STRIKETHROUGH, objective.toText(), TextStyles.RESET, Text.NEW_LINE ) );
        } );

        return objectives.build();
    }

    @Override
    public Text getFormattedRewards () {
        Text.Builder rewards = Text.builder();
        rewards.append( Text.of( "Rewards:\n" ) );
        quest.getRewards().forEach( reward -> {
            rewards.append( Text.of( reward.toText(), Text.NEW_LINE ) );
        } );
        return rewards.build();
    }
}
