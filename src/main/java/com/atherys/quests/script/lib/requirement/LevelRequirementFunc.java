package com.atherys.quests.script.lib.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quest.requirement.Requirements;

import java.util.function.Function;

/**
 * @jsfunc
 */ 
public class LevelRequirementFunc implements Function<Integer, Requirement> {

    @Override
    public Requirement apply(Integer level) {
        return Requirements.level(level);
    }
}
