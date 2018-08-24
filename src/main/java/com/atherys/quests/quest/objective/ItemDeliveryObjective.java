package com.atherys.quests.quest.objective;

import com.atherys.quests.api.objective.AbstractObjective;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.util.ItemUtils;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.PlayerInventory;
import org.spongepowered.api.text.Text;

import java.util.UUID;

public class ItemDeliveryObjective extends AbstractObjective<InteractEntityEvent.Secondary> {

    @Expose
    private ItemStack item;
    @Expose
    private UUID target;
    @Expose
    private boolean complete;
    @Expose
    private Text targetName;


    private ItemDeliveryObjective(){
        super(InteractEntityEvent.Secondary.class);
    }

    ItemDeliveryObjective(ItemStack item, UUID target, Text targetName){
        this();
        this.item = item;
        this.target = target;
        this.targetName = targetName;
    }

    @Override
    protected void onNotify(InteractEntityEvent.Secondary event, Quester quester) {
        if(!event.getTargetEntity().getUniqueId().equals(target)) return;
        PlayerInventory inventory = (PlayerInventory) quester.getCachedPlayer().getInventory();
        if(inventory.contains(item) && !inventory.getEquipment().contains(item)){
            ItemUtils.removeItemExact(inventory, item);
            complete = true;
        }
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public ItemDeliveryObjective copy() {
        return new ItemDeliveryObjective(item, target, targetName);
    }

    @Override
    public Text toText() {
        return Text.builder().append(Text.of("Deliver ", item.getQuantity(), " ", ItemUtils.getItemName(item)
                , item.getQuantity() > 1 ? "s":"", " to ", targetName, "."))
                .build();
    }
}