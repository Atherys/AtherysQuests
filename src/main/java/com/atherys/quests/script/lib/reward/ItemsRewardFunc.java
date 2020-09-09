package com.atherys.quests.script.lib.reward;

import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.quest.reward.Rewards;
import com.atherys.script.api.function.ScriptBiFunction;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.List;

/**
 * @jsfunc
 */
public class ItemsRewardFunc implements ScriptBiFunction<Text, List<ItemStack>, Reward> {
    /**
     * A reward to give the player an item.
     *
     * @param description The description for the items.
     * @param items The items to reward the player.
     * @jsname itemsReward
     */
    @Override
    public Reward call(Text description, List<ItemStack> items) {
        return Rewards.items(description, items);
    }
}
