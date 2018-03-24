package com.atherys.quests;

import com.atherys.quests.data.DialogData;
import com.atherys.quests.data.QuestData;
import com.google.common.reflect.TypeToken;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataRegistration;
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

        DIALOG = Key.builder()
                .type( new TypeToken<Value<String>>() {
                } )
                .id( "atherysquests:dialog" )
                .name( "Dialog" )
                .query( DataQuery.of( "Dialog" ) )
                .build();

        DataRegistration.builder()
                .dataClass( DialogData.class )
                .immutableClass( DialogData.Immutable.class )
                .builder( new DialogData.Builder() )
                .dataName( "Dialog" )
                .manipulatorId( "dialog" )
                .buildAndRegister( Sponge.getPluginManager().fromInstance( this ).get() );

        event.register( DIALOG );

        QUEST = Key.builder()
                .type( new TypeToken<Value<String>>() {
                } )
                .id( "atherysquests:quest" )
                .name( "Quest" )
                .query( DataQuery.of( "Quest" ) )
                .build();

        DataRegistration.builder()
                .dataClass( QuestData.class )
                .immutableClass( QuestData.Immutable.class )
                .builder( new QuestData.Builder() )
                .dataName( "Quest" )
                .manipulatorId( "quest" )
                .buildAndRegister( Sponge.getPluginManager().fromInstance( this ).get() );

        event.register( QUEST );
    }

}
