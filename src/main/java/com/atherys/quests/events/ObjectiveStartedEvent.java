package com.atherys.quests.events;

import com.atherys.quests.quest.objective.Objective;
import com.atherys.quests.quester.Quester;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public class ObjectiveStartedEvent implements Event {

    private Cause cause;

    private Objective objective;
    private Quester quester;

    public ObjectiveStartedEvent ( Objective objective, Quester quester ) {
        this.objective = objective;
        this.quester = quester;
        cause = Cause.builder()
                .append( quester.getCachedPlayer() )
                .build(Sponge.getCauseStackManager().getCurrentContext() );
    }

    @Override
    public Cause getCause() {
        return cause;
    }

    public Objective getObjective() {
        return objective;
    }

    public Quester getQuester() {
        return quester;
    }

}
