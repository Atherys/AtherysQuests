package com.atherys.quests.event.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.util.AbstractEvent;

public abstract class AbstractQuestEvent extends AbstractEvent {

    private Quest quest;
    private Quester simpleQuester;

    public AbstractQuestEvent(Quest quest, Quester simpleQuester) {
        super(quest, simpleQuester);
        this.quest = quest;
        this.simpleQuester = simpleQuester;
    }

    public Quest getQuest() {
        return quest;
    }

    public Quester getQuester() {
        return simpleQuester;
    }
}
