package com.atherys.quests.services;

import org.spongepowered.api.item.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class InventoryService {

    private static InventoryService instance = new InventoryService();

    private Map<Inventory, Consumer<Inventory>> inventories = new HashMap<>();

    private InventoryService() {
    }

    public static InventoryService getInstance() {
        return instance;
    }

    public boolean hasInventory(Inventory inventory) {
        return inventories.containsKey(inventory);
    }

    public void addInventory(Inventory inventory, Consumer<Inventory> closeAction) {
        if (!hasInventory(inventory)) inventories.put(inventory, closeAction);
    }

    public void removeInventory(Inventory container) {
        if (hasInventory(container)) {
            inventories.get(container).accept(container);
            inventories.remove(container);
        }
    }
}
