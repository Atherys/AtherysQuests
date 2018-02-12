package com.atherys.quests.quest.objective;

import com.atherys.quests.events.ObjectiveCompletedEvent;
import com.atherys.quests.events.ObjectiveProgressedEvent;
import com.atherys.quests.events.ObjectiveStartedEvent;
import com.atherys.quests.quester.Quester;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;

public abstract class AbstractObjective<T extends Event> implements Objective {

    protected Class<T> eventClass;

    @Expose private boolean started;

    protected AbstractObjective ( Class<T> eventClass ) {
        this.eventClass = eventClass;
    }

    @Override
    public void notify ( Event event, Quester quester ) {
        if ( !event.getClass().equals(eventClass)) return;

        if ( !started ) {
            started = true;
            Sponge.getEventManager().post( new ObjectiveStartedEvent( this, quester ) );
        }

        if ( !isComplete() ) {
            onNotify( eventClass.cast(event), quester );

            if ( !isComplete() ) {
                Sponge.getEventManager().post( new ObjectiveProgressedEvent( this, quester ) );
            } else {
                Sponge.getEventManager().post( new ObjectiveCompletedEvent( this, quester ) );
            }
        }
    }

    protected abstract void onNotify ( T event, Quester quester );

}
