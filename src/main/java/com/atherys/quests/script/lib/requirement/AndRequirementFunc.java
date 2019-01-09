package com.atherys.quests.script.lib.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.model.quest.requirement.Requirements;

import java.util.function.BiFunction;

/**
 * @jsfunc
 */ 
public class AndRequirementFunc implements BiFunction<Requirement, Requirement, Requirement> {
    /**
     * A requirement that requires two requirements to be met.
     * @jsname andRequirement
     * @param requirement The first requirement.
     * @param requirement2 The second requirement.
     */
    @Override
    public Requirement apply(Requirement requirement, Requirement requirement2) {
        return Requirements.and(requirement, requirement2);
    }
}
