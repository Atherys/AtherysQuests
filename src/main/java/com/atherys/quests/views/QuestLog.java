package com.atherys.quests.views;

import com.atherys.core.views.View;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.quester.Quester;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextStyles;

import java.util.ArrayList;
import java.util.List;

public class QuestLog implements View<Quester> {

    private final Quester quester;

    public QuestLog ( Quester quester ) {
        this.quester = quester;
    }

    @Override
    public void show ( Player player ) {
        BookView.Builder log = BookView.builder();

        List<Text> pages = new ArrayList<>();
        Text.Builder lastPage = Text.builder();

        lastPage.append( Text.of( "Quest Log:\n" ) );

        int i = 1;
        for ( Quest quest : quester.getQuests().values() ) {
            Text.Builder questView = Text.builder();
            questView.append( Text.of( "[", i, "] " ) );
            questView.append( Text.of( quest.isComplete() ? TextStyles.STRIKETHROUGH : TextStyles.NONE, quest.getName(), TextStyles.NONE ) );
            questView.onHover( TextActions.showText( Text.of( "Click to view more details." ) ) );
            questView.onClick( TextActions.executeCallback( src -> quest.createView().show( player ) ) );

            if ( i % 7 == 0 ) {
                pages.add( lastPage.build() );
                lastPage = Text.builder();
            } else {
                lastPage.append( Text.of( questView.build(), "\n" ) );
            }

            i++;
        }

        pages.add( lastPage.build() );

        log.addPages( pages );

        player.sendBookView( log.build() );
    }

    public Quester getQuester () {
        return quester;
    }
}
