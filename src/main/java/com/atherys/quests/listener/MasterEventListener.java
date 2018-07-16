package com.atherys.quests.listener;

import com.atherys.quests.managers.QuesterManager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStateEvent;

public class MasterEventListener {

    @Listener
    public void onEvent(Event event) {
        if (event instanceof GameStateEvent) return;
        event.getCause().first(Player.class).ifPresent(player -> QuesterManager.getInstance().notify(event, player));
    }

}
