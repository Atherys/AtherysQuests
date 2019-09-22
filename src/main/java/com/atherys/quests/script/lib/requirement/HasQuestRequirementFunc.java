package com.atherys.quests.script.lib.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quest.requirement.Requirements;
import com.atherys.script.api.function.ScriptFunction;

/**
 * @jsfunc
 */
public class HasQuestRequirementFunc implements ScriptFunction<String, Requirement> {
    /**
     * A requirement for a player to have a quest.
     *
     * @param questId The quest's ID.
     * @jsname hasQuestRequirement
     */
    @Override
    public Requirement call(String questId) {
        return Requirements.hasQuest(questId);
    }
}
