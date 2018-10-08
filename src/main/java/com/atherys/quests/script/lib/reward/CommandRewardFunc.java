package com.atherys.quests.script.lib.reward;

import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.quest.reward.Rewards;
import org.spongepowered.api.text.Text;

import java.util.function.BiFunction;

/**
 * @jsfunc
 */ 
public class CommandRewardFunc implements BiFunction<String, Text, Reward> {
    @Override
    public Reward apply(String command, Text description) {
        return Rewards.command(command, description);
    }
}
