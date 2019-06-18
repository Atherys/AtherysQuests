package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.quest.Quest;
import com.atherys.script.api.function.ScriptFunction;

/**
 * @jsfunc
 */
public class GetQuestObjectives implements ScriptFunction<Quest, Objective[]> {
    @Override
    public Objective[] call(Quest quest) {
        return (Objective[]) quest.getObjectives().toArray();
    }
}
