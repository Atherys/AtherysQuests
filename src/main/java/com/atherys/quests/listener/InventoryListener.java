package com.atherys.quests.listener;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.service.InventoryService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;

@Singleton
public class InventoryListener {

    @Inject
    InventoryService inventoryService;

    @Listener
    public void onInventoryClose(InteractInventoryEvent.Close event) {
        inventoryService.removeInventory(event.getTargetInventory());
    }

}
