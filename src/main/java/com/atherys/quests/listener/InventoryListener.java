package com.atherys.quests.listener;

import com.atherys.quests.AtherysQuests;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;

public class InventoryListener {

    @Listener
    public void onInventoryClose(InteractInventoryEvent.Close event) {
        AtherysQuests.getInstance().getInventoryService().removeInventory(event.getTargetInventory());
    }

}
