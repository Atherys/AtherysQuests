package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.reward.Reward;
import com.atherys.script.api.function.ScriptBiFunction;

import java.util.List;

/**
 * @jsfunc
 */
public class AddQuestRewards implements ScriptBiFunction<Quest, List<Reward>, Boolean> {
    @Override
    public Boolean call(Quest quest, List<Reward> rewards) {
        for (Reward reward : rewards) {
            quest.getRewards().add(reward);
        }

        return true;
    }
}
