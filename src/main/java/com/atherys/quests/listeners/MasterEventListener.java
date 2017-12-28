package com.atherys.quests.listeners;

import com.atherys.quests.quester.QuesterManager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;

public class MasterEventListener {

    @Listener
    public void onEvent ( Event event, @First Player player ) {
        QuesterManager.getInstance().notify ( event, player );
    }

}
