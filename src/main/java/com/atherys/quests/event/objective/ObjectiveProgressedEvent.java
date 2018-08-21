package com.atherys.quests.event.objective;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.quester.Quester;

public class ObjectiveProgressedEvent extends AbstractObjectiveEvent {
    public ObjectiveProgressedEvent(Objective objective, Quester quester) {
        super(objective, quester);
    }
}
