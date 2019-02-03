package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.quest.Quest;

import java.util.function.Function;

/**
 * @jsfunc
 */
public class GetQuestObjectives implements Function<Quest, Objective[]> {
    @Override
    public Objective[] apply(Quest quest) {
        return (Objective[]) quest.getObjectives().toArray();
    }
}
