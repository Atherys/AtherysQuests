package com.atherys.quests.script.lib.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.model.quest.requirement.Requirements;

import java.util.function.Function;

/**
 * @jsfunc
 */ 
public class NotRequirementFunc implements Function<Requirement, Requirement> {
    /**
     * A requirement for the player _not_ to have a requirement.
     * @jsname notRequirement
     * @param requirement The requirement for them not to have.
     */
    @Override
    public Requirement apply(Requirement requirement) {
        return Requirements.not(requirement);
    }
}
