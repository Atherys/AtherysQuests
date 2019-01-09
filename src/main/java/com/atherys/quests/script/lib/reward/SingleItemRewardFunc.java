package com.atherys.quests.script.lib.reward;

import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.model.quest.reward.Rewards;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.function.Function;

/**
 * @jsfunc
 */ 
public class SingleItemRewardFunc implements Function<ItemStack, Reward> {
    /**
     * A reward to give the player an item.
     * @jsname singleItemReward
     * @param itemStack The item to reward the player.
     */
    @Override
    public Reward apply(ItemStack itemStack) {
        return Rewards.item(itemStack);
    }
}
