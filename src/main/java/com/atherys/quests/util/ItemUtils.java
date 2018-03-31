package com.atherys.quests.util;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.List;

public final class ItemUtils {

    public static Item dropItemStack( ItemStack stack, World world, Vector3d position ) {
        Item item = ( Item ) world.createEntity( EntityTypes.ITEM, position );
        item.offer( Keys.REPRESENTED_ITEM, stack.createSnapshot() );
        world.spawnEntity( item );

        return item;
    }

    public static List<ItemStack> getItemsInInventory( Inventory inventory ) {
        List<ItemStack> items = new ArrayList<>();
        inventory.slots().forEach( slot -> slot.peek().ifPresent( items::add ) );
        return items;
    }

}
