package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.reward.Reward;

import java.util.function.Function;

/**
 * @jsfunc
 */ 
public class GetQuestRewards implements Function<Quest, Reward[]> {
    @Override
    public Reward[] apply(Quest quest) {
        return (Reward[]) quest.getRewards().toArray();
    }
}
