package com.atherys.quests.listeners;

import com.atherys.quests.managers.InventoryManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;

public class InventoryListener {

    @Listener
    public void onInventoryClose( InteractInventoryEvent.Close event ) {
        InventoryManager.getInstance().removeInventory( event.getTargetInventory() );
    }

}
