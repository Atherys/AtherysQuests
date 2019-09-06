package com.atherys.quests.quest.reward;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.util.ItemUtils;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;

import static org.spongepowered.api.text.format.TextColors.GOLD;

/**
 * A reward for giving the player items.
 */
public class ItemsReward implements Reward {

    @Expose
    private final List<ItemStackSnapshot> items;

    @Expose
    private Text description;

    ItemsReward() {
        items = new ArrayList<>();
        description = Text.EMPTY;
    }

    ItemsReward(Text description, List<ItemStack> itemStacks) {
        this();
        this.description = description;
        itemStacks.forEach(itemStack -> items.add(itemStack.createSnapshot()));
    }

    @Override
    public Reward copy() {
        return this;
    }

    @Override
    public Text toText() {
        return description;
    }

    @Override
    public boolean award(Quester quester) {
        Player player = AtherysQuests.getInstance().getQuesterService().getCachedPlayer(quester);
        if (player == null || !player.isOnline() || player.isRemoved()) return false;

        // Create chest inventory
        Inventory inventory = Inventory.builder()
                .property(InventoryDimension.PROPERTY_NAME, InventoryDimension.of(9, 3))
                .property(InventoryTitle.of(Text.of(GOLD, "Quest Rewards")))
                .build(AtherysQuests.getInstance());


        // put all itemstacks inside
        for (ItemStackSnapshot itemStackSnapshot : items) {
            inventory.offer(itemStackSnapshot.createStack());
        }

        // send inventory to player
        player.openInventory(inventory);

        // upon closing the inventory, drop all items which have not been picked up to the ground
        AtherysQuests.getInstance().getInventoryService().addInventory(inventory, (container) -> {
            ItemUtils.getItemsInInventory(container).forEach(item -> {
                ItemUtils.dropItemStack(item, player.getWorld(), player.getLocation().getPosition());
            });
        });

        return false;
    }
}
