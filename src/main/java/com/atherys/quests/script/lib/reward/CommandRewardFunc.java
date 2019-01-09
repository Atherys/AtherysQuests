package com.atherys.quests.script.lib.reward;

import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.model.quest.reward.Rewards;
import org.spongepowered.api.text.Text;

import java.util.function.BiFunction;

/**
 * @jsfunc
 */ 
public class CommandRewardFunc implements BiFunction<String, Text, Reward> {
    /**
     * Performs a command as the reward for a quest.
     * @jsname commandReward
     * @param command The command to perform.
     * @param description A description of the reward.
     */
    @Override
    public Reward apply(String command, Text description) {
        return Rewards.command(command, description);
    }
}
