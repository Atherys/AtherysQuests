package com.atherys.quests.script.lib.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quest.requirement.Requirements;
import com.atherys.script.api.function.ScriptFunction;

/**
 * @jsfunc
 */
public class LevelRequirementFunc implements ScriptFunction<Integer, Requirement> {
    /**
     * A requirement for the player to be a certain level (experience).
     *
     * @param level The level, as an integer.
     * @jsname levelRequirement
     */
    @Override
    public Requirement call(Integer level) {
        return Requirements.level(level);
    }
}
