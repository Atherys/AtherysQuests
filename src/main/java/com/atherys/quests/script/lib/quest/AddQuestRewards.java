package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.reward.Reward;

import java.util.List;
import java.util.function.BiFunction;

/**
 * @jsfunc
 */
public class AddQuestRewards implements BiFunction<Quest, List<Reward>, Boolean> {
    @Override
    public Boolean apply(Quest quest, List<Reward> rewards) {
        for (Reward reward : rewards) {
            quest.getRewards().add(reward);
        }

        return true;
    }
}
