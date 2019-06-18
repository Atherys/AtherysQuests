package com.atherys.quests.script.lib.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quest.requirement.Requirements;
import com.atherys.script.api.function.ScriptFunction;

/**
 * @jsfunc
 */
public class CompletedQuestRequirementFunc implements ScriptFunction<String, Requirement> {
    /**
     * A requirement for having turned in a quest.
     *
     * @param questId The quest's ID.
     * @jsname turnedInQuestRequirement
     */
    @Override
    public Requirement call(String questId) {
        return Requirements.turnedInQuest(questId);
    }
}
