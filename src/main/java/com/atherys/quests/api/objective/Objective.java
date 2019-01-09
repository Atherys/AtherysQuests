package com.atherys.quests.api.objective;

import com.atherys.quests.api.base.Observer;
import com.atherys.quests.api.base.Prototype;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.TextRepresentable;

/**
 * Represents a task the player must accomplish in order for the {@link Quest} containing it to be completed.<br>
 * Objectives are copyable objects. When a {@link Quester} picks up a new Quest, the completedQuest object, along with all of it's member variables ( including Objectives ), are copied and stored on to the SimpleQuester object.<br>
 * Objectives are event observers. This is the method through which they know when something significant has happened that they may wish to take note of.<br>
 * Objectives are {@link TextRepresentable}s, meaning they are responsible for properly formatting their own data for displaying to the player.<br><br>
 * Extending the {@link AbstractObjective} class is the preferred method for introducing new Objective types.
 */
public interface Objective<T extends Objective<T>> extends Prototype<Objective>, Observer<Event>, TextRepresentable {

    /**
     * @return Whether or not this objective has been completed.
     */
    boolean isComplete();

}
