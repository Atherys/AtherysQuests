package com.atherys.quests.events;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.quester.Quester;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public class QuestStartedEvent implements Event {

    private Cause cause;

    private Quest quest;
    private Quester quester;

    public QuestStartedEvent( Quest quest, Quester quester ) {
        this.quest = quest;
        this.quester = quester;
        cause = Cause.builder()
                .append( quester.getCachedPlayer() )
                .build( Sponge.getCauseStackManager().getCurrentContext() );
    }

    @Override
    public Cause getCause() {
        return cause;
    }

    public Quester getQuester() {
        return quester;
    }

    public Quest getQuest() {
        return quest;
    }
}
