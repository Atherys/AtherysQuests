package com.atherys.quests.script.lib.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quest.requirement.Requirements;

import java.util.function.Function;

/**
 * @jsfunc
 */ 
public class HasQuestRequirementFunc implements Function<String, Requirement> {
    /**
     * A requirement for a player to have a quest.
     * @jsname hasQuestRequirement
     * @param questId The quest's ID.
     * @return
     */
    @Override
    public Requirement apply(String questId) {
        return Requirements.hasQuest(questId);
    }
}
