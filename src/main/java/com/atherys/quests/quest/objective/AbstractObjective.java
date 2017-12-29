package com.atherys.quests.quest.objective;

import com.atherys.quests.quester.Quester;
import org.spongepowered.api.event.Event;

public abstract class AbstractObjective<T extends Event> implements Objective {

    protected Class<T> eventClass;

    private boolean started;

    protected AbstractObjective ( Class<T> eventClass ) {
        this.eventClass = eventClass;
    }

    @Override
    public void notify ( Event event, Quester quester ) {
        if ( !event.getClass().equals(eventClass)) return;

        if ( !started ) {
            started = true;
            // TODO: Trigger ObjectiveStartedEvent
        }

        if ( !isComplete() ) {
            onNotify( eventClass.cast(event), quester );
            // TODO: Trigger ObjectiveProgressedEvent

            if ( isComplete() ) {
                // TODO: Trigger ObjectiveFinishedEvent
            }
        }
    }

    protected abstract void onNotify ( T event, Quester quester );

}
