package com.atherys.quests.quest;

import com.atherys.quests.api.base.Observer;
import com.atherys.quests.api.base.Prototype;
import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.util.CopyUtils;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.event.Event;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * An essential part of {@link StagedQuest}s, a Stage represents the next step in completing the Quest.
 * A stage may also offer the player rewards for having completed it.
 * Stages differ from Quests in that they do not have Requirements, and cannot exist without a StagedQuest.
 */
public class Stage implements Observer<Event>, Prototype<Stage> {

    @Expose
    private Objective objective;

    @Expose
    private List<Reward> rewards = new ArrayList<>();

    public Stage(Objective objective) {
        this(objective, null);
    }

    public Stage(Objective objective, @Nullable List<Reward> rewards) {
        this.objective = objective;
        if (rewards != null) this.rewards = rewards;
    }

    private Stage(Stage stage) {
        this.objective = CopyUtils.copy(stage.getObjective());
        this.rewards = CopyUtils.copyList(stage.getRewards());
    }

    public Objective getObjective() {
        return objective;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    @Override
    public void notify(Event event, Quester quester) {
        objective.notify(event, quester);
    }

    public void award(Quester quester) {
        rewards.forEach(reward -> reward.award(quester));
    }

    @Override
    public Stage copy() {
        return new Stage(this);
    }

    public boolean isComplete() {
        return objective.isComplete();
    }
}
