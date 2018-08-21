package com.atherys.quests.util;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.PlayerInventory;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.List;

public final class ItemUtils {

    public static Item dropItemStack(ItemStack stack, World world, Vector3d position) {
        Item item = (Item) world.createEntity(EntityTypes.ITEM, position);
        item.offer(Keys.REPRESENTED_ITEM, stack.createSnapshot());
        world.spawnEntity(item);

        return item;
    }

    public static List<ItemStack> getItemsInInventory(Inventory inventory) {
        List<ItemStack> items = new ArrayList<>();
        inventory.slots().forEach(slot -> slot.peek().ifPresent(items::add));
        return items;
    }

    public static int getItemCount(PlayerInventory inventory, ItemStack itemStack) {

        ItemStack copy = itemStack.copy();

        return inventory.query(QueryOperationTypes.ITEM_STACK_IGNORE_QUANTITY.of(copy)).totalItems();
    }

    public static void removeItemExact(PlayerInventory inventory, ItemStack itemStack) {
        inventory.query(QueryOperationTypes.ITEM_STACK_EXACT.of(itemStack)).poll();
    }

    public static Text getItemName(ItemStack itemStack) {
        return itemStack.get(Keys.DISPLAY_NAME).orElse(Text.of(itemStack.getTranslation()));
    }

}
