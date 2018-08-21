package com.atherys.quests.event.objective;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.util.AbstractEvent;

public abstract class AbstractObjectiveEvent extends AbstractEvent {

    private Objective objective;
    private Quester quester;

    public AbstractObjectiveEvent(Objective objective, Quester quester) {
        super(objective, quester);
        this.objective = objective;
        this.quester = quester;
    }

    public Objective getObjective() {
        return objective;
    }

    public Quester getQuester() {
        return quester;
    }
}
