package com.atherys.quests.script.lib.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quest.requirement.Requirements;

import java.util.function.BiFunction;

public class OrRequirementFunc implements BiFunction<Requirement, Requirement, Requirement> {
    @Override
    public Requirement apply(Requirement requirement, Requirement requirement2) {
        return Requirements.or(requirement, requirement2);
    }
}
