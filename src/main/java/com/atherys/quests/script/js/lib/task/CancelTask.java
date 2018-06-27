package com.atherys.quests.script.js.lib.task;

import org.spongepowered.api.scheduler.Task;

import java.util.function.Function;

public class CancelTask implements Function<Task, Boolean> {
    @Override
    public Boolean apply(Task task) {
        return task.cancel();
    }
}
