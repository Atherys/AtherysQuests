package com.atherys.quests.util;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import javax.annotation.Nonnull;

public class AbstractEvent implements Event {

    private Cause cause;

    protected AbstractEvent(Object... objects) {
        Cause.Builder causeBuilder = Cause.builder();

        for (Object object : objects) {
            causeBuilder.append(object);
        }

        this.cause = causeBuilder.build(Sponge.getCauseStackManager().getCurrentContext());
    }

    @Override
    @Nonnull
    public Cause getCause() {
        return cause;
    }
}
