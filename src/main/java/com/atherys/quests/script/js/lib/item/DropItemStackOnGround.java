package com.atherys.quests.script.js.lib.item;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.function.BiFunction;

public class DropItemStackOnGround implements BiFunction<ItemStack, Location<World>, Entity> {
    @Override
    public Entity apply(ItemStack itemStack, Location<World> location) {
        Entity item = location.getExtent().createEntity(EntityTypes.ITEM, location.getPosition());
        item.offer(Keys.REPRESENTED_ITEM, itemStack.createSnapshot());
        location.getExtent().spawnEntity(item);
        return item;
    }
}
