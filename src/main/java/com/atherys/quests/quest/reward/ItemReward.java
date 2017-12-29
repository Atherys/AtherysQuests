package com.atherys.quests.quest.reward;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.managers.InventoryManager;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.util.CopyUtils;
import com.atherys.quests.util.ItemUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.item.inventory.type.GridInventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemReward implements Reward {

    private List<ItemStack> items = new ArrayList<>();

    public ItemReward ( List<ItemStack> items ) {
        this.items = items;
    }

    public static ItemReward of ( ItemStack... items ) {
        return new ItemReward( Arrays.asList( items ) );
    }

    @Override
    public boolean award ( Quester quester ) {
        Player player = quester.getCachedPlayer();
        if ( player == null || !player.isOnline() || !player.isRemoved() ) return false;

        if ( player.getInventory().query( Hotbar.class, GridInventory.class ).size() < items.size() ) {

            // Create chest inventory
            Inventory inventory = Inventory.builder().of( InventoryArchetypes.CHEST ).build( AtherysQuests.getInstance() );

            // put all itemstacks inside
            items.forEach(inventory::offer);

            // send inventory to player
            if ( player.getOpenInventory().isPresent() ) player.closeInventory();
            player.openInventory( inventory );

            // upon closing the inventory, drop all items which have not been picked up to the ground
            InventoryManager.getInstance().addInventory( inventory, (container) -> {
                ItemUtils.getItemsInInventory(container).forEach( item -> {
                    ItemUtils.dropItemStack( item, player.getWorld(), player.getLocation().getPosition() );
                });
            });

        } else items.forEach( itemstack -> player.getInventory().offer(itemstack) );
        return true;
    }

    @Override
    public Reward copy() {
        return new ItemReward( CopyUtils.copyItemStackList( this.items ) );
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
