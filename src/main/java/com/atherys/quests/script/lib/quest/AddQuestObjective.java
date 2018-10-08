package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.quest.SimpleQuest;

import java.util.function.BiFunction;

/**
 * @jsfunc
 */ 
public class AddQuestObjective implements BiFunction<SimpleQuest, Objective, Boolean> {
    @Override
    public Boolean apply(SimpleQuest simpleQuest, Objective objective) {
        return simpleQuest.getObjectives().add(objective);
    }
}
