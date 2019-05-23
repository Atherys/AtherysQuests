package com.atherys.quests.views;

import com.atherys.quests.api.quest.Quest;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;

public interface QuestView<T extends Quest> {

    Text getFormattedRequirements();

    Text getFormattedObjectives();

    Text getFormattedRewards();

    Text getCompletion(Player player);

    BookView toBookView(Player player);

    default void show(Player player) {
        player.sendBookView(toBookView(player));
    }
}
