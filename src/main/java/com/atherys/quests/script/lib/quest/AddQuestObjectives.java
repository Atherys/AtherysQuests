package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.quest.SimpleQuest;
import com.atherys.script.api.function.ScriptBiFunction;

import java.util.List;

/**
 * @jsfunc
 */
public class AddQuestObjectives implements ScriptBiFunction<SimpleQuest, List<Objective>, Boolean> {
    @Override
    public Boolean call(SimpleQuest simpleQuest, List<Objective> objectives) {
        for (Objective objective : objectives) {
            simpleQuest.getObjectives().add(objective);
        }

        return true;
    }
}
