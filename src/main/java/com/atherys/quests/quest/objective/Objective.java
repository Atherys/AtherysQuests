package com.atherys.quests.quest.objective;

import com.atherys.quests.base.Observer;
import com.atherys.quests.base.Prototype;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.TextRepresentable;

/**
 * Represents a task the player must accomplish in order for the {@link com.atherys.quests.quest.Quest} containing it to be completed.<br>
 * Objectives are copyable objects. When a {@link com.atherys.quests.quester.Quester} picks up a new Quest, the quest object, along with all of it's member variables ( including Objectives ),
 * are copied and stored on to the Quester object.<br>
 * Objectives are also event observers. This is the method through which they know when something significant has happened that they may wish to take note of.
 */
@ConfigSerializable
public interface Objective<T extends Objective<T>> extends Prototype<Objective>, Observer<Event>, TextRepresentable {

    /**
     * @return Whether or not this objective has been completed.
     */
    boolean isComplete();

}
