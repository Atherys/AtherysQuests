package com.atherys.quests.script.js.lib.item;

import org.spongepowered.api.entity.Item;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.function.BiFunction;
import java.util.function.Function;

public class DropItemStackOnGround implements BiFunction<ItemStack, Location<World>, Item> {
    @Override
    public Item apply(ItemStack itemStack, Location<World> location) {
        return null;
    }
}
