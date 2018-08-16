package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;

import java.util.function.Function;

public class GetQuestRequirements implements Function<Quest, Requirement[]> {
    @Override
    public Requirement[] apply(Quest quest) {
        return (Requirement[]) quest.getRequirements().toArray();
    }
}
