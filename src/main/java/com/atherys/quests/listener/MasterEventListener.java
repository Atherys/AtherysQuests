package com.atherys.quests.listener;

import com.atherys.quests.service.QuesterService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStateEvent;

@Singleton
public class MasterEventListener {

    @Inject
    QuesterService questerService;

    MasterEventListener() {
    }

    @Listener
    public void onEvent(Event event) {
        if (event instanceof GameStateEvent) return;
        event.getCause().first(Player.class).ifPresent(player -> questerService.notify(event, player));
    }

}
