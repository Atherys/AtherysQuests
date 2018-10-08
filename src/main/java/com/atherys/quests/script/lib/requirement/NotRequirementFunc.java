package com.atherys.quests.script.lib.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quest.requirement.Requirements;

import java.util.function.Function;

/**
 * @jsfunc
 */ 
public class NotRequirementFunc implements Function<Requirement, Requirement> {
    @Override
    public Requirement apply(Requirement requirement) {
        return Requirements.not(requirement);
    }
}
