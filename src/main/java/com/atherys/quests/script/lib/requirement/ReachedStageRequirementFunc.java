package com.atherys.quests.script.lib.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quest.requirement.Requirements;
import com.atherys.script.api.function.ScriptBiFunction;

/**
 * @jsfunc
 */
public class ReachedStageRequirementFunc implements ScriptBiFunction<String, Integer, Requirement> {
    /**
     * A requirement for the player to have reached a specific stage of a quest. Only works with
     * staged quests.
     * @param questId The ID of the quest.
     * @param stage The index of the stage to check for.
     * @jsname reachedStageRequirement
     */
    @Override
    public Requirement call(String questId, Integer stage) {
        return Requirements.reachedStage(questId, stage);
    }
}
