package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.reward.Reward;
import com.atherys.script.api.function.ScriptFunction;

/**
 * @jsfunc
 */
public class GetQuestRewards implements ScriptFunction<Quest, Reward[]> {
    @Override
    public Reward[] call(Quest quest) {
        return (Reward[]) quest.getRewards().toArray();
    }
}
