package com.atherys.quests.event.quest;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.model.SimpleQuester;

public class SimpleQuestProgressEvent extends AbstractQuestEvent {

    private Objective objective;

    public SimpleQuestProgressEvent(Quest quest, Objective objective, Quester simpleQuester) {
        super(quest, simpleQuester);
        this.objective = objective;
    }

    public Objective getObjective() {
        return objective;
    }
}
