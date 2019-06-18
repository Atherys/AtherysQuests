package com.atherys.quests.script.lib.objective;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.quest.objective.Objectives;
import com.atherys.script.api.function.ScriptBiFunction;

/**
 * @jsfunc
 */
public class KillEntityObjectiveFunc implements ScriptBiFunction<String, Integer, Objective> {
    /**
     * An objective that requires killing a number of entities. The entity name is in the format "minecraft:entity".
     *
     * @param entityName The name of the entity. ie: "minecraft:zombie".
     * @param amount     The number of entities to kill.
     * @jsname killEntityObjective
     */
    @Override
    public Objective call(String entityName, Integer amount) {
        return Objectives.killEntity(entityName, amount);
    }
}
