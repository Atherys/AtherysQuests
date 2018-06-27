package com.atherys.quests.script.js.lib.item;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.function.Function;

public class GetItemStackLore implements Function<ItemStack, Text[]> {
    @Override
    public Text[] apply(ItemStack itemStack) {
        return itemStack.get(Keys.ITEM_LORE).map(list -> (Text[]) list.toArray()).orElse(null);
    }
}
