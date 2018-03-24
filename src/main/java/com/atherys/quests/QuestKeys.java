package com.atherys.quests;

import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameRegistryEvent;

public final class QuestKeys {

    QuestKeys() {
    }

    public static Key<Value<String>> DIALOG;

    public static Key<Value<String>> QUEST;

    @Listener
    public void onKeyRegistration( GameRegistryEvent.Register<Key<?>> event ) {

    }

}
