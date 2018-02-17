package com.atherys.quests.listeners;

import com.atherys.quests.managers.QuesterManager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.Listener;

public class MasterEventListener {

    @Listener
    public void onEvent ( Event event ) {
        event.getCause().first(Player.class).ifPresent(player -> QuesterManager.getInstance().notify(event, player));
    }

}
