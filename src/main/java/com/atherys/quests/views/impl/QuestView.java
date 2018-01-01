package com.atherys.quests.views.impl;

import com.atherys.quests.quest.Quest;
import com.atherys.quests.views.api.View;
import com.atherys.quests.views.api.ViewBook;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;

public class QuestView implements View, ViewBook {

    private Quest quest;

    private QuestView ( Quest quest ) {
        this.quest = quest;
    }

    @Override
    public BookView toBook() {
        BookView.Builder view = BookView.builder();
        return view.build();
    }

    @Override
    public void show ( Player player ) {
        player.sendBookView( toBook() );
    }
}
