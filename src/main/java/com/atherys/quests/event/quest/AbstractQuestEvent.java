package com.atherys.quests.event.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.util.AbstractEvent;

public abstract class AbstractQuestEvent extends AbstractEvent {

    private Quest quest;
    private Quester quester;

    public AbstractQuestEvent(Quest quest, Quester quester) {
        super(quest, quester);
        this.quest = quest;
        this.quester = quester;
    }

    public Quest getQuest() {
        return quest;
    }

    public Quester getQuester() {
        return quester;
    }
}
