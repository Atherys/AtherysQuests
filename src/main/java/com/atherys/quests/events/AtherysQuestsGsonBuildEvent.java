package com.atherys.quests.events;

import com.atherys.quests.AtherysQuests;
import com.google.gson.GsonBuilder;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public class AtherysQuestsGsonBuildEvent implements Event {

    private GsonBuilder builder;
    private Cause cause;

    public AtherysQuestsGsonBuildEvent(GsonBuilder builder) {
        this.builder = builder;
        this.cause = Cause.builder()
                .append(AtherysQuests.getInstance())
                .append(builder)
                .build(Sponge.getCauseStackManager().getCurrentContext());
    }

    @Override
    public Cause getCause() {
        return cause;
    }

    public GsonBuilder getBuilder() {
        return builder;
    }
}
