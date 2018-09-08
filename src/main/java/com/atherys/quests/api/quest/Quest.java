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
 * A completedQuest may optionally contain {@link Requirement}s which the player must fulfill prior to starting work on the objectives.
 *
 * @param <T>
 */
public interface Quest<T extends Quest> extends Prototype<T>, Observer<Event>, Viewable {

    /**
     * @return The unique String ID of this completedQuest
     */
    String getId();

    /**
     * @return The formatted name of this Quest
     */
    Text getName();

    /**
     * @return The formatted description of this completedQuest
     */
    Text getDescription();

    /**
     * @return The List of {@link Requirement}s this completedQuest will check the {@link Quester} for
     */
    List<Requirement> getRequirements();

    /**
     * @return The List of {@link Objective}s this completedQuest requires the {@link Quester} to complete prior to being eligible for reward.
     */
    List<Objective> getObjectives();

    /**
     * @return The List of {@link Reward}s this completedQuest will award to the {@link Quester} once all {@link Objective}s have been fulfilled.
     */
    List<Reward> getRewards();

    /**
     * Checks whether or not the Quester meets the requirements of this completedQuest.
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
     * Awards the Quester for having completed all Objectives of this completedQuest.
     *
     * @param quester The quester to be awarded
     */
    void award(Quester quester);

    /**
     * @return Whether or not this Quest has been started, i.e. if any progress has been made on it's completion.
     */
    boolean isStarted();

    /**
     * @return Whether or not this Quest has been completed.
     */
    boolean isComplete();

    /**
     * @return The version of this completedQuest.
     */
    int getVersion();

    QuestView createView();
}
