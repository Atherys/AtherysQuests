package com.atherys.quests.script.lib.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quest.requirement.Requirements;

import java.util.function.Function;

public class HasQuestRequirementFunc implements Function<String, Requirement> {
    @Override
    public Requirement apply(String questId) {
        return Requirements.hasQuest(questId);
    }
}
