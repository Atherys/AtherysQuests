package com.atherys.quests.quest.objective;

import org.spongepowered.api.event.Event;

public abstract class AbstractObjective<T extends Event> implements Objective<Event> {

    protected Class<T> eventClass;

    protected AbstractObjective ( Class<T> eventClass ) {
        this.eventClass = eventClass;
    }

    @Override
    public void notify ( Event event ) {
        if ( !isComplete() && event.getClass().equals( eventClass ) ) onNotify ( eventClass.cast(event) );
    }

    protected abstract void onNotify ( T event );

}
