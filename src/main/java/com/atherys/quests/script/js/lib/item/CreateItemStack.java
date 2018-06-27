package com.atherys.quests.script.js.lib.item;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;
import java.util.function.BiFunction;

public class CreateItemStack implements BiFunction<String, Integer, ItemStack> {

    CreateItemStack() {}

    @Override
    public ItemStack apply(String itemTypeId, Integer amount) {
        Optional<ItemType> type = Sponge.getRegistry().getType(ItemType.class, itemTypeId);
        return type.map(itemType ->
                ItemStack.builder()
                        .itemType(itemType)
                        .quantity(amount)
                        .build()
        ).orElse(null);
    }

}
