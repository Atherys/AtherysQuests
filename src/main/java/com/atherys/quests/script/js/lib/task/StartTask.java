package com.atherys.quests.script.js.lib.task;

import com.atherys.quests.AtherysQuests;
import org.spongepowered.api.scheduler.Task;

import java.util.function.Function;

public class StartTask implements Function<Task.Builder, Task> {
    @Override
    public Task apply(Task.Builder builder) {
        return builder.submit(AtherysQuests.getInstance());
    }
}
