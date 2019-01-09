package com.atherys.quests.event.objective;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.model.SimpleQuester;
import com.atherys.quests.util.AbstractEvent;

public abstract class AbstractObjectiveEvent extends AbstractEvent {

    private Objective objective;
    private Quester simpleQuester;

    public AbstractObjectiveEvent(Objective objective, Quester simpleQuester) {
        super(objective, simpleQuester);
        this.objective = objective;
        this.simpleQuester = simpleQuester;
    }

    public Objective getObjective() {
        return objective;
    }

    public Quester getQuester() {
        return simpleQuester;
    }
}
