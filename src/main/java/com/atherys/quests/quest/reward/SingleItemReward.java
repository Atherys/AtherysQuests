package com.atherys.quests.quest.reward;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.managers.InventoryManager;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.util.ItemUtils;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

public class SingleItemReward implements Reward {

    @Expose
    private ItemStackSnapshot item;

    private SingleItemReward() {
    }

    public SingleItemReward( ItemStack stack ) {
        this.item = stack.createSnapshot();
    }

    private SingleItemReward( ItemStackSnapshot snapshot ) {
        this.item = snapshot;
    }

    @Override
    public Reward copy() {
        return new SingleItemReward( item );
    }

    @Override
    public Text toText() {
        return Text.builder().append( Text.of( "Single Item Reward" ) ).onHover( TextActions.showItem( item ) ).build();
    }

    @Override
    public boolean award( Quester quester ) {
        Player player = quester.getCachedPlayer();
        AtherysQuests.getInstance().getLogger().info( "Triggered SingleItemReward" );
        if ( player == null || !player.isOnline() || player.isRemoved() ) return false;

        AtherysQuests.getInstance().getLogger().info( "Passed player online and valid checks" );

        // Create chest inventory
        Inventory inventory = Inventory.builder()
                .of( InventoryArchetypes.SLOT )
                .property( InventoryTitle.of( Text.of( "Quest Item Reward" ) ) )
                .build( AtherysQuests.getInstance() );


        // put all itemstacks inside
        inventory.offer( item.createStack() );

        // send inventory to player
        if ( player.getOpenInventory().isPresent() ) player.closeInventory();
        player.openInventory( inventory );

        // upon closing the inventory, drop all items which have not been picked up to the ground
        InventoryManager.getInstance().addInventory( inventory, ( container ) -> {
            ItemUtils.getItemsInInventory( container ).forEach( item -> {
                ItemUtils.dropItemStack( item, player.getWorld(), player.getLocation().getPosition() );
            } );
        } );

        return false;
    }
}
