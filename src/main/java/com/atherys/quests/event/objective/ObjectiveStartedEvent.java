package com.atherys.quests.event.objective;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.quester.Quester;

public class ObjectiveStartedEvent extends AbstractObjectiveEvent {
    public ObjectiveStartedEvent(Objective objective, Quester simpleQuester) {
        super(objective, simpleQuester);
    }
}
