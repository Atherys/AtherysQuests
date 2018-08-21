package com.atherys.quests.event.quest;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.quester.Quester;

public class SimpleQuestProgressEvent extends AbstractQuestEvent {

    private Objective objective;

    public SimpleQuestProgressEvent(Quest quest, Objective objective, Quester quester) {
        super(quest, quester);
        this.objective = objective;
    }

    public Objective getObjective() {
        return objective;
    }
}
