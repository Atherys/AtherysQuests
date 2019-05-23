package com.atherys.quests.listener;

import com.atherys.quests.event.AtherysQuestsGsonBuildEvent;
import com.google.inject.Singleton;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;

@Singleton
public class GsonListener {

    GsonListener() {
    }

    @Listener(order = Order.FIRST)
    public void onGsonBuild(AtherysQuestsGsonBuildEvent event) {
    }
}
