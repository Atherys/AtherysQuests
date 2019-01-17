package com.atherys.quests.views;

import com.atherys.quests.api.quest.Quest;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public interface QuestView<T extends Quest> {

    Text getFormattedRequirements();

    Text getFormattedObjectives();

    Text getFormattedRewards();

    void show(Player player);

}
