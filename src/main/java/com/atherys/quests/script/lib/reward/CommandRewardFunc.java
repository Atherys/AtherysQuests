package com.atherys.quests.script.lib.reward;

import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.quest.reward.Rewards;
import com.atherys.script.api.function.ScriptBiFunction;
import org.spongepowered.api.text.Text;

/**
 * @jsfunc
 */
public class CommandRewardFunc implements ScriptBiFunction<String, Text, Reward> {
    /**
     * Performs a command as the reward for a quest.
     *
     * @param command     The command to perform.
     * @param description A description of the reward.
     * @jsname commandReward
     */
    @Override
    public Reward call(String command, Text description) {
        return Rewards.command(command, description);
    }
}
