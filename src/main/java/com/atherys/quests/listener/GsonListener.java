package com.atherys.quests.listener;

import com.atherys.core.gson.TypeAdapters;
import com.atherys.quests.event.AtherysQuestsGsonBuildEvent;
import com.atherys.quests.util.CompactTextAdapter;
import com.google.inject.Singleton;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

@Singleton
public class GsonListener {

    GsonListener() {
    }

    @Listener(order = Order.FIRST)
    public void onGsonBuild(AtherysQuestsGsonBuildEvent event) {
        TypeAdapters.registerCatalogTypes(
                event.getBuilder(),
                Currency.class);
        TypeAdapters.registerSerializables(
                event.getBuilder(),
                ItemStackSnapshot.class,
                BlockSnapshot.class,
                Location.class);
        event.getBuilder().registerTypeAdapter(Text.class, new CompactTextAdapter());
    }
}
