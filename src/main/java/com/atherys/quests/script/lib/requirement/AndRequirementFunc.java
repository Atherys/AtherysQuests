package com.atherys.quests.script.lib.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quest.requirement.Requirements;
import com.atherys.script.api.function.ScriptBiFunction;

/**
 * @jsfunc
 */
public class AndRequirementFunc implements ScriptBiFunction<Requirement, Requirement, Requirement> {
    /**
     * A requirement that requires two requirements to be met.
     *
     * @param requirement  The first requirement.
     * @param requirement2 The second requirement.
     * @jsname andRequirement
     */
    @Override
    public Requirement call(Requirement requirement, Requirement requirement2) {
        return Requirements.and(requirement, requirement2);
    }
}
