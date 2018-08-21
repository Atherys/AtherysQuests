package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.reward.Reward;

import java.util.function.BiFunction;

public class AddQuestReward implements BiFunction<Quest, Reward, Boolean> {
    @Override
    public Boolean apply(Quest quest, Reward reward) {
        return quest.getRewards().add(reward);
    }
}
