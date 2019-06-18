package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.reward.Reward;
import com.atherys.script.api.function.ScriptBiFunction;

/**
 * @jsfunc
 */
public class AddQuestReward implements ScriptBiFunction<Quest, Reward, Boolean> {
    @Override
    public Boolean call(Quest quest, Reward reward) {
        return quest.getRewards().add(reward);
    }
}
