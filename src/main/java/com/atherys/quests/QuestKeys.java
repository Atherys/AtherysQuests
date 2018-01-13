package com.atherys.quests;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameRegistryEvent;

public final class QuestKeys {

    QuestKeys() {}

    public final static Key<Value<String>> DIALOG = Key.builder()
            .id( "atherysquests:dialog" )
            .name( "Dialog" )
            .type(new TypeToken<Value<String>>(){})
            .query( DataQuery.of("Dialog") )
            .build();

    public final static Key<Value<String>> QUEST = Key.builder()
            .id( "atherysquests:quest" )
            .name( "Quest" )
            .type(new TypeToken<Value<String>>(){})
            .query( DataQuery.of("Quest") )
            .build();

    @Listener
    public void onKeyRegistration (GameRegistryEvent.Register<Key<?>> event) {
        event.register( DIALOG );
        event.register( QUEST  );
    }

}
