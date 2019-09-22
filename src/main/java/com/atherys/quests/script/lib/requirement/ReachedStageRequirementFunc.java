package com.atherys.quests.script.lib.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quest.requirement.Requirements;
import com.atherys.script.api.function.ScriptBiFunction;

public class ReachedStageRequirementFunc implements ScriptBiFunction<String, Integer, Requirement> {
    @Override
    public Requirement call(String questId, Integer stage) {
        return Requirements.reachedStage(questId, stage);
    }
}
