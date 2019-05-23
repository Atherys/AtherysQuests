package com.atherys.quests.views;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public interface CompletableView {

    Text getCompletion(Player player);
}
