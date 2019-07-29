package com.atherys.quests.api.quest.modifiers;

import com.atherys.quests.api.base.Prototype;
import com.google.gson.annotations.Expose;

import java.time.Duration;

public class RepeatableComponent implements Prototype<RepeatableComponent> {
    @Expose
    private final int limit;

    @Expose
    private final Duration cooldown;

    public RepeatableComponent(int limit, Duration cooldown) {
        this.limit = limit;
        this.cooldown = cooldown;
    }

    public int getLimit() {
        return limit;
    }

    public Duration getCooldown() {
        return cooldown;
    }

    @Override
    public RepeatableComponent copy() {
        return new RepeatableComponent(limit, cooldown);
    }
}
