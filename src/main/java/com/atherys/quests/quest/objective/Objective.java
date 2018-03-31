package com.atherys.quests.quest.objective;

import com.atherys.quests.base.Observer;
import com.atherys.quests.base.Prototype;
import com.atherys.quests.quest.Quest;
import com.atherys.quests.quester.Quester;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.TextRepresentable;

/**
 * Represents a task the player must accomplish in order for the {@link Quest} containing it to be completed.<br>
 * Objectives are copyable objects. When a {@link Quester} picks up a new Quest, the quest object, along with all of it's member variables ( including Objectives ), are copied and stored on to the Quester object.<br>
 * Objectives are event observers. This is the method through which they know when something significant has happened that they may wish to take note of.<br>
 * Objectives are {@link TextRepresentable}s, meaning they are responsible for properly formatting their own data for displaying to the player.<br><br>
 * It should be noted, extending the {@link AbstractObjective} class is the preferred method for introducing new Objective types.
 */
@ConfigSerializable
public interface Objective<T extends Objective<T>> extends Prototype<Objective>, Observer<Event>, TextRepresentable {

    /**
     * @return Whether or not this objective has been completed.
     */
    boolean isComplete();

}
