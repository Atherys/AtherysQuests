package com.atherys.quests.listeners;

import com.atherys.core.gson.ConfigurateAdapter;
import com.atherys.quests.events.AtherysQuestsGsonBuildEvent;
import com.atherys.quests.util.CompactTextAdapter;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;

public class GsonListener {

    @Listener( order = Order.FIRST )
    public void onGsonBuild ( AtherysQuestsGsonBuildEvent event ) {
        event.getBuilder()
                .registerTypeAdapter( Text.class, new CompactTextAdapter() )
                .registerTypeAdapter( ItemStackSnapshot.class, new ConfigurateAdapter<>( ItemStackSnapshot.class ) )
                .registerTypeAdapter( Currency.class, new ConfigurateAdapter<>( Currency.class ) )
                .registerTypeAdapter( BlockSnapshot.class, new ConfigurateAdapter<>( BlockSnapshot.class ) );
    }

}
