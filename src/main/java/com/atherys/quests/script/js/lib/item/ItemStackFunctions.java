package com.atherys.quests.script.js.lib.item;

import com.atherys.quests.script.js.lib.ScriptFunctions;
import org.spongepowered.api.item.inventory.ItemStack;

import javax.script.ScriptEngine;

/**
 * Functions responsible for the creation and manipulation of {@link ItemStack )s
 */
public final class ItemStackFunctions implements ScriptFunctions {

    @Override
    public void put(ScriptEngine engine) {
        engine.put("createItemStack", new CreateItemStack());

        // Getters
        engine.put("getItemStackEnchants", new GetItemStackEnchants());
        engine.put("getItemStackDisplayName", new GetItemStackDisplayName());
        engine.put("getItemStackLore", new GetItemStackLore());

        // Setters
        engine.put("setItemStackEnchants", new SetItemStackEnchants());
        engine.put("setItemStackDisplayName", new SetItemStackDisplayName());
        engine.put("setItemStackLore", new SetItemStackLore());

        // Utilities
        engine.put("isItemStack", new IsItemStack());
        engine.put("compareItemStacks", new CompareItemStacks());
        engine.put("dropItemStackOnGround", new DropItemStackOnGround());
    }

}
