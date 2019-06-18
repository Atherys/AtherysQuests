package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.script.api.function.ScriptFunction;

/**
 * @jsfunc
 */
public class GetQuestRequirements implements ScriptFunction<Quest, Requirement[]> {
    @Override
    public Requirement[] call(Quest quest) {
        return (Requirement[]) quest.getRequirements().toArray();
    }
}
