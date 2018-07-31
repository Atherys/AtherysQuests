package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;

import java.util.function.BiFunction;

public class AddQuestRequirements implements BiFunction<Quest, Requirement[], Boolean> {
    @Override
    public Boolean apply(Quest quest, Requirement[] requirements) {
        for (Requirement requirement : requirements) {
            quest.getRequirements().add(requirement);
        }

        return true;
    }
}
