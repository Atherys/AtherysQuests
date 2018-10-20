package com.atherys.quests.script.lib.objective;

import com.atherys.quests.quest.objective.ItemDeliveryObjective;
import com.atherys.quests.quest.objective.Objectives;
import com.atherys.script.api.util.TriFunction;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.UUID;

/**
 * @jsfunc
 */ 
public class ItemDeliveryFunc implements TriFunction<ItemStack, String, Text, ItemDeliveryObjective> {
    /**
     * An objective that requires delivering an item to an entity.
     * @param itemStack
     * @param targetUUID
     * @param targetName
     * @return
     */
    @Override
    public ItemDeliveryObjective apply(ItemStack itemStack, String targetUUID, Text targetName) {
        return Objectives.itemDelivery(itemStack.createSnapshot(), UUID.fromString(targetUUID), targetName);
    }
}
