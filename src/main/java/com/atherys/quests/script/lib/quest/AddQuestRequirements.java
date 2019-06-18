package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.script.api.function.ScriptBiFunction;

import java.util.List;

/**
 * @jsfunc
 */
public class AddQuestRequirements implements ScriptBiFunction<Quest, List<Requirement>, Boolean> {
    @Override
    public Boolean call(Quest quest, List<Requirement> requirements) {
        for (Requirement requirement : requirements) {
            quest.getRequirements().add(requirement);
        }

        return true;
    }
}
