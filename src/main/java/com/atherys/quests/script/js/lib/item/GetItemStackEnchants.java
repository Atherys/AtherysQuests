package com.atherys.quests.script.js.lib.item;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.function.Function;

public class GetItemStackEnchants implements Function<ItemStack, Enchantment[]> {
    @Override
    public Enchantment[] apply(ItemStack itemStack) {
        return itemStack.get(Keys.ITEM_ENCHANTMENTS).map(enchantments -> (Enchantment[]) enchantments.toArray()).orElse(null);
    }
}
