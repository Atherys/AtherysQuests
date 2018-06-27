package com.atherys.quests.script.js.lib.task;

import org.spongepowered.api.scheduler.Task;

import java.util.function.BiFunction;
import java.util.function.Function;

public class SetTaskExecutable implements BiFunction<Task.Builder, Runnable, Boolean> {

    @Override
    public Boolean apply(Task.Builder builder, Runnable runnable) {
        builder.execute(runnable);
        return true;
    }
}
