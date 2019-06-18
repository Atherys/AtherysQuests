package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.quest.SimpleQuest;
import com.atherys.script.api.function.ScriptBiFunction;

/**
 * @jsfunc
 */
public class AddQuestObjective implements ScriptBiFunction<SimpleQuest, Objective, Boolean> {
    @Override
    public Boolean call(SimpleQuest simpleQuest, Objective objective) {
        return simpleQuest.getObjectives().add(objective);
    }
}
