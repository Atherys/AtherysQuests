package com.atherys.quests.script.lib.objective;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.quest.objective.Objectives;

import java.util.function.BiFunction;

/**
 * @jsfunc
 */ 
public class KillEntityObjectiveFunc implements BiFunction<String, Integer, Objective> {
    @Override
    public Objective apply(String entityName, Integer amount) {
        return Objectives.killEntity(entityName, amount);
    }
}
