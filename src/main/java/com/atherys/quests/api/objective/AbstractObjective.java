package com.atherys.quests.api.objective;

import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.entity.SimpleQuester;
import com.atherys.quests.event.objective.ObjectiveCompletedEvent;
import com.atherys.quests.event.objective.ObjectiveProgressedEvent;
import com.atherys.quests.event.objective.ObjectiveStartedEvent;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;

/**
 * An abstract implementation of {@link Objective} which checks whether or not the Objective's event corresponds to the event being supplied via {@link #notify(Event, SimpleQuester)}.<br>
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
    public void notify(Event event, Quester simpleQuester) {
        if (!eventClass.isAssignableFrom(event.getClass())) return;

        // If this objective hasn't been started yet, post an ObjectiveStartedEvent and mark as started
        if (!started) {
            started = true;
            Sponge.getEventManager().post(new ObjectiveStartedEvent(this, simpleQuester));
        }

        // If this objective hasn't been completed yet, notify
        if (!isComplete()) {
            onNotify(eventClass.cast(event), simpleQuester);

            // If it still hasn't been completed after notification, post an ObjectiveProgressedEvent
            if (!isComplete()) {
                Sponge.getEventManager().post(new ObjectiveProgressedEvent(this, simpleQuester));
                // Otherwise, post an ObjectiveCompletedEvent
            } else {
                Sponge.getEventManager().post(new ObjectiveCompletedEvent(this, simpleQuester));
            }
        }
    }

    protected abstract void onNotify(T event, Quester simpleQuester);

}
