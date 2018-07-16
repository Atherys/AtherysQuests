package com.atherys.quests.listener;

import com.atherys.quests.service.InventoryService;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;

public class InventoryListener {

    @Listener
    public void onInventoryClose(InteractInventoryEvent.Close event) {
        InventoryService.getInstance().removeInventory(event.getTargetInventory());
    }

}
