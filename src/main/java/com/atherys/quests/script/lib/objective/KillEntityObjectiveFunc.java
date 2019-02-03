package com.atherys.quests.script.lib.objective;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.quest.objective.Objectives;

import java.util.function.BiFunction;

/**
 * @jsfunc
 */
public class KillEntityObjectiveFunc implements BiFunction<String, Integer, Objective> {
    /**
     * An objective that requires killing a number of entities. The entity name is in the format "minecraft:entity".
     *
     * @param entityName The name of the entity. ie: "minecraft:zombie".
     * @param amount     The number of entities to kill.
     * @jsname killEntityObjective
     */
    @Override
    public Objective apply(String entityName, Integer amount) {
        return Objectives.killEntity(entityName, amount);
    }
}
