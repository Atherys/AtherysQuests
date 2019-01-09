package com.atherys.quests.model.quest.objective;

import com.atherys.quests.api.objective.AbstractObjective;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.model.SimpleQuester;
import com.atherys.quests.util.ItemUtils;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.entity.PlayerInventory;
import org.spongepowered.api.text.Text;

import java.util.UUID;

public class ItemDeliveryObjective extends AbstractObjective<InteractEntityEvent.Secondary> {

    @Expose
    private ItemStackSnapshot item;
    @Expose
    private UUID target;
    @Expose
    private boolean complete;
    @Expose
    private Text targetName;


    private ItemDeliveryObjective(){
        super(InteractEntityEvent.Secondary.class);
    }

    ItemDeliveryObjective(ItemStackSnapshot item, UUID target, Text targetName){
        this();
        this.item = item;
        this.target = target;
        this.targetName = targetName;
    }

    @Override
    protected void onNotify(InteractEntityEvent.Secondary event, Quester quester) {
        if(!event.getTargetEntity().getUniqueId().equals(target)) return;
        PlayerInventory inventory = (PlayerInventory) quester.getCachedPlayer().getInventory();
        if(inventory.contains(item.createStack()) && !inventory.getEquipment().contains(item.createStack())){
            ItemUtils.removeItemExact(inventory, item.createStack());
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
        return Text.builder().append(Text.of("Deliver ", item.getQuantity(), " ", ItemUtils.getItemName(item.createStack())
                , item.getQuantity() > 1 ? "s":"", " to ", targetName, "."))
                .build();
    }

}