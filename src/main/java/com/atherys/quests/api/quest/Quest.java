package com.atherys.quests.api.quest;

import com.atherys.core.views.Viewable;
import com.atherys.quests.api.base.Observer;
import com.atherys.quests.api.base.Prototype;
import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.views.QuestView;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.Text;

import java.util.List;

/**
 * Common interface for all Quest classes. <br>
 * A Quest represents a series of tasks ( {@link Objective}s ), which once completed will reward the player with {@link Reward}(s).
 * A quest may optionally contain {@link Requirement}s which the player must fulfill prior to starting work on the objectives.
 *
 * @param <T>
 */
public interface Quest<T extends Quest> extends Prototype<T>, Observer<Event>, Viewable {

    /**
     * @return The unique String ID of this quest
     */
    String getId();

    /**
     * @return The formatted name of this Quest
     */
    Text getName();

    /**
     * @return The formatted description of this quest
     */
    Text getDescription();

    /**
     * @return The List of {@link Requirement}s this quest will check the {@link Quester} for
     */
    List<Requirement> getRequirements();

    /**
     * @return The List of {@link Objective}s this quest requires the {@link Quester} to complete prior to being eligible for reward.
     */
    List<Objective> getObjectives();

    /**
     * @return The List of {@link Reward}s this quest will award to the {@link Quester} once all {@link Objective}s have been fulfilled.
     */
    List<Reward> getRewards();

    /**
     * A method which gets called when this quest is picked up by a {@link Quester}.
     * This method can be overridden to provide additional functionality
     *
     * @param quester The quester who picked up the quest
     */
    void pickUp(Quester quester);

    /**
     * Checks whether or not the Quester meets the requirements of this quest.
     *
     * @param quester The Quester to be checked
     * @return Whether or not the Quester meets the requirements
     */
    boolean meetsRequirements(Quester quester);

    /**
     * Notifies the {@link Quest} of any {@link Event} relevant to the given {@link Quester}
     *
     * @param event   The event which was triggered
     * @param quester The quester responsible for triggering it
     */
    void notify(Event event, Quester quester);

    /**
     * Awards the Quester for having completed all Objectives of this quest.
     *
     * @param quester The quester to be awarded
     */
    void award(Quester quester);

    /**
     * A method which gets called when this quest is completed ( not turned in ) by a {@link Quester}.
     * This method can be overridden to provide additional functionality
     *
     * @param quester The quester completing the quest
     */
    void complete(Quester quester);

    /**
     * A method which gets called when this quest is turned in by a {@link Quester}.
     * This method can be overridden to provide additional functionality
     *
     * @param quester The quester turning in the quest
     */
    void turnIn(Quester quester);

    /**
     * @return Whether or not this Quest has been started, i.e. if any progress has been made on it's completion.
     */
    boolean isStarted();

    /**
     * @return Whether or not this Quest has been completed.
     */
    boolean isComplete();

    /**
     * @return The version of this quest.
     */
    int getVersion();

    QuestView createView();
}
