package com.atherys.quests.script.lib.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quest.requirement.Requirements;
import com.atherys.script.api.function.ScriptFunction;

/**
 * @jsfunc
 */
public class NotRequirementFunc implements ScriptFunction<Requirement, Requirement> {
    /**
     * A requirement for the player _not_ to have a requirement.
     *
     * @param requirement The requirement for them not to have.
     * @jsname notRequirement
     */
    @Override
    public Requirement call(Requirement requirement) {
        return Requirements.not(requirement);
    }
}
