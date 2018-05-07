package com.atherys.quests.api.objective;

import com.atherys.quests.events.ObjectiveCompletedEvent;
import com.atherys.quests.events.ObjectiveProgressedEvent;
import com.atherys.quests.events.ObjectiveStartedEvent;
import com.atherys.quests.quester.Quester;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;

/**
 * An abstract implementation of {@link Objective} which checks whether or not the Objective's event corresponds to the event being supplied via {@link #notify(Event, Quester)}.<br>
 * This is the preferred method for introducing new Objectives, as any such would require code of this sort to filter out irrelevant events. This class does nothing more than abstract away the necessary boilerplate.
 *
 * @param <T> The event class this objective listens for
 */
public abstract class AbstractObjective<T extends Event> implements Objective {

    protected Class<T> eventClass;

    @Expose
    private boolean started;

    protected AbstractObjective(Class<T> eventClass) {
        this.eventClass = eventClass;
    }

    @Override
    public void notify(Event event, Quester quester) {
        if(!eventClass.isAssignableFrom(event.getClass())) return;

        // If this objective hasn't been started yet, post an ObjectiveStartedEvent and mark as started
        if(!started) {
            started = true;
            Sponge.getEventManager().post(new ObjectiveStartedEvent(this, quester));
        }

        // If this objective hasn't been completed yet, notify
        if(!isComplete()) {
            onNotify(eventClass.cast(event), quester);

            // If it still hasn't been completed after notification, post an ObjectiveProgressedEvent
            if(!isComplete()) {
                Sponge.getEventManager().post(new ObjectiveProgressedEvent(this, quester));
                // Otherwise, post an ObjectiveCompletedEvent
            } else {
                Sponge.getEventManager().post(new ObjectiveCompletedEvent(this, quester));
            }
        }
    }

    protected abstract void onNotify(T event, Quester quester);

}
