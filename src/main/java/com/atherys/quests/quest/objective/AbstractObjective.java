package com.atherys.quests.quest.objective;

import org.spongepowered.api.event.Event;

public abstract class AbstractObjective<T extends Event> implements Objective<T> {

    protected Class<T> eventClass;

    protected AbstractObjective ( Class<T> eventClass ) {
        this.eventClass = eventClass;
    }

    @Override
    public void notify ( T event ) {
        if ( isComplete() || !event.getClass().equals( eventClass ) ) return;
        else onNotify ( event );
    }

    protected abstract void onNotify ( T event );

}
