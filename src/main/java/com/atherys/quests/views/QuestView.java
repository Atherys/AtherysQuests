package com.atherys.quests.views;

import com.atherys.core.views.AbstractView;
import com.atherys.quests.quest.Quest;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextStyles;

public class QuestView extends AbstractView<Quest, QuestView> {

    protected QuestView( Quest object ) {
        super(object);
    }

    @Override
    public void show(Player player) {
        BookView.Builder questView = BookView.builder();

        Text.Builder intro = Text.builder();
        intro.append( Text.of ( object.getName(), "\n\n" ) );
        intro.append( object.getDescription() );

        questView.addPage( intro.build() );

        Text.Builder objectives = Text.builder();
        objectives.append( Text.of( "Objective:\n" ) );
        object.getObjectives().forEach( objective -> {
            objectives.append( Text.of( objective.isComplete() ? TextStyles.NONE : TextStyles.STRIKETHROUGH, objective.toText(), "\n" ) );
        });

        questView.addPage( objectives.build() );

        Text.Builder rewards = Text.builder();
        objectives.append( Text.of( "Objective:\n" ) );
        object.getRewards().forEach( reward -> {
            rewards.append( Text.of( reward.toText(), "\n" ) );
        });

        questView.addPage( rewards.build() );

        player.sendBookView( questView.build() );
    }
}
