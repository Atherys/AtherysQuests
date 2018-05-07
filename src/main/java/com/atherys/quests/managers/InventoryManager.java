package com.atherys.quests.managers;

import org.spongepowered.api.item.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class InventoryManager {

    private static InventoryManager instance = new InventoryManager();

    private Map<Inventory, Consumer<Inventory>> inventories = new HashMap<>();

    private InventoryManager() {
    }

    public boolean hasInventory(Inventory inventory) {
        return inventories.containsKey(inventory);
    }

    public void addInventory(Inventory inventory, Consumer<Inventory> closeAction) {
        if(!hasInventory(inventory)) inventories.put(inventory, closeAction);
    }

    public void removeInventory(Inventory container) {
        if(hasInventory(container)) {
            inventories.get(container).accept(container);
            inventories.remove(container);
        }
    }

    public static InventoryManager getInstance() {
        return instance;
    }
}
