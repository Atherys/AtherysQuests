package com.atherys.quests.script.lib.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.model.quest.requirement.Requirements;

import java.util.function.Function;

/**
 * @jsfunc
 */ 
public class CompletedQuestRequirementFunc implements Function<String, Requirement> {
    /**
     * A requirement for completing a quest.
     * @jsname completedQuestRequirement
     * @param questId The quest's ID.
     */
    @Override
    public Requirement apply(String questId) {
        return Requirements.completedQuest(questId);
    }
}
