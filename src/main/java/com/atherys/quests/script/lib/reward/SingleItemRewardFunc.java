package com.atherys.quests.script.lib.reward;

import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.quest.reward.Rewards;
import com.atherys.script.api.function.ScriptFunction;
import org.spongepowered.api.item.inventory.ItemStack;

/**
 * @jsfunc
 */
public class SingleItemRewardFunc implements ScriptFunction<ItemStack, Reward> {
    /**
     * A reward to give the player an item.
     *
     * @param itemStack The item to reward the player.
     * @jsname singleItemReward
     */
    @Override
    public Reward call(ItemStack itemStack) {
        return Rewards.item(itemStack);
    }
}
