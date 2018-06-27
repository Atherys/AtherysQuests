package com.atherys.quests.script.js.lib.item;

import com.sun.org.apache.xpath.internal.functions.Function;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.function.BiFunction;

public class SetItemStackDisplayName implements BiFunction<ItemStack, Text, Boolean> {

    SetItemStackDisplayName() {}

    @Override
    public Boolean apply(ItemStack itemStack, Text text) {
        return itemStack.offer(Keys.DISPLAY_NAME, text).isSuccessful();
    }
}
