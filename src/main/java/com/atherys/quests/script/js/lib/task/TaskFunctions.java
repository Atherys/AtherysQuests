package com.atherys.quests.script.js.lib.task;

import com.atherys.quests.script.js.lib.ScriptFunctions;
import org.spongepowered.api.scheduler.Task;

import javax.script.ScriptEngine;

public final class TaskFunctions implements ScriptFunctions {
    @Override
    public void put(ScriptEngine engine) {
        engine.put("buildTask", new TaskOf());
        engine.put("setTaskDelay", new SetTaskDelay());
        engine.put("setTaskInterval", new SetTaskInterval());
        engine.put("setTaskExecutable", new SetTaskExecutable());
        engine.put("startTask", new StartTask());
        engine.put("cancelTask", new CancelTask());
    }
}
