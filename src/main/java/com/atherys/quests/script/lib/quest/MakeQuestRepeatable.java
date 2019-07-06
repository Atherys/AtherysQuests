package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quest.modifiers.RepeatableComponent;
import com.atherys.script.api.function.ScriptTriFunction;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class MakeQuestRepeatable implements ScriptTriFunction<Quest, Integer, Integer, Boolean> {
    @Override
    public Boolean call(Quest quest, Integer minutes, Integer limit) {
        RepeatableComponent component = new RepeatableComponent(limit, Duration.of(minutes, ChronoUnit.MINUTES));
        quest.makeRepeatable(component);
        return true;
    }
}
