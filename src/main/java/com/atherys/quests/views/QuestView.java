package com.atherys.quests.views;

import com.atherys.core.views.View;
import com.atherys.quests.quest.Quest;
import com.atherys.quests.quest.QuestMsg;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextStyles;

public class QuestView implements View<Quest> {

    private final Quest quest;

    public QuestView( Quest quest ) {
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

        Text.Builder objectives = Text.builder();
        objectives.append( Text.of( "Objectives:\n" ) );
        quest.getObjectives().forEach( objective -> {
            objectives.append( Text.of( !objective.isComplete() ? TextStyles.NONE : TextStyles.STRIKETHROUGH, objective.toText(), Text.NEW_LINE ) );
        } );

        questView.addPage( objectives.build() );

        Text.Builder rewards = Text.builder();
        objectives.append( Text.of( "Rewards:\n" ) );
        quest.getRewards().forEach( reward -> {
            rewards.append( Text.of( reward.toText(), "\n" ) );
        } );

        questView.addPage( rewards.build() );

        return questView.build();
    }

    public Text getFormattedRequirements() {
        Text.Builder reqText = Text.builder();
        reqText.append( Text.of( QuestMsg.MSG_PREFIX, " Quest Requirements: " ) );
        quest.getRequirements().forEach( requirement -> reqText.append( Text.of( QuestMsg.MSG_PREFIX, " * ", requirement.toText(), Text.NEW_LINE ) ) );
        return reqText.build();
    }
}
