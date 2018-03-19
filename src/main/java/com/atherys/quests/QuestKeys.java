package com.atherys.quests;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameRegistryEvent;

public final class QuestKeys {

    QuestKeys() {
    }

    public static final Key<Value<String>> DIALOG = Key.builder()
            .type( new TypeToken<Value<String>>() {
            } )
            .id( "atherysquests:dialog" )
            .name( "Dialog" )
            .query( DataQuery.of( "Dialog" ) )
            .build();

    public static final Key<Value<String>> QUEST = Key.builder()
            .type( new TypeToken<Value<String>>() {
            } )
            .id( "atherysquests:quest" )
            .name( "Quest" )
            .query( DataQuery.of( "Quest" ) )
            .build();

    @Listener
    public void onKeyRegistration( GameRegistryEvent.Register<Key<?>> event ) {
        event.register( DIALOG );
        event.register( QUEST );
    }

}
