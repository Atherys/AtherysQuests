package com.atherys.quests.quest.reward;

import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.api.reward.Reward;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;

public class MultiItemReward implements Reward {
    @Expose
    private List<ItemStackSnapshot> items;

    @Expose
    private Text description;

    MultiItemReward() {
        items = new ArrayList<>();
    }

    MultiItemReward(ItemStack...itemStacks) {
        this();
        for (ItemStack itemStack : itemStacks) {
            items.add(itemStack.createSnapshot());
        }
    }

    @Override
    public boolean award(Quester quester) {
        return false;
    }

    @Override
    public Reward copy() {
        return null;
    }

    @Override
    public Text toText() {
        return description;
    }
}
