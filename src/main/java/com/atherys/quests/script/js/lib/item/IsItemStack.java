package com.atherys.quests.script.js.lib.item;

import org.spongepowered.api.item.inventory.ItemStack;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public class IsItemStack implements Predicate {
    @Override
    public boolean test(Object itemStack) {
        return itemStack instanceof ItemStack;
    }
}
