package com.atherys.quests.event.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public class QuestTurnedInEvent implements Event {

    private Cause cause;

    private Quest quest;
    private Quester simpleQuester;

    public QuestTurnedInEvent(Quest quest, Quester simpleQuester) {
        this.quest = quest;
        this.simpleQuester = simpleQuester;
        cause = Cause.builder()
                .append(simpleQuester)
                .build(Sponge.getCauseStackManager().getCurrentContext());
    }

    @Override
    public Cause getCause() {
        return cause;
    }

    public Quester getQuester() {
        return simpleQuester;
    }

    public Quest getQuest() {
        return quest;
    }

}
