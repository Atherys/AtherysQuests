package com.atherys.quests.listeners;

import com.atherys.core.gson.ConfigurateAdapter;
import com.atherys.quests.events.AtherysQuestsGsonBuildEvent;
import com.atherys.quests.quest.objective.DialogObjective;
import com.atherys.quests.quest.objective.KillEntityObjective;
import com.atherys.quests.quest.requirement.*;
import com.atherys.quests.quest.reward.MoneyReward;
import com.atherys.quests.quest.reward.MultiItemReward;
import com.atherys.quests.quest.reward.SingleItemReward;
import com.atherys.quests.util.CompactTextAdapter;
import com.atherys.quests.util.GsonUtils;
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
                .registerTypeAdapter( Currency.class, new ConfigurateAdapter<>( Currency.class ) );

        GsonUtils.getRequirementRuntimeTypeAdapterFactory()
                .registerSubtype( AndRequirement.class )
                .registerSubtype( OrRequirement.class )
                .registerSubtype( LevelRequirement.class )
                .registerSubtype( MoneyRequirement.class )
                .registerSubtype( QuestRequirement.class );

        GsonUtils.getObjectiveTypeAdapterFactory()
                .registerSubtype( KillEntityObjective.class )
                .registerSubtype( DialogObjective.class );

        GsonUtils.getRewardRuntimeTypeAdapterFactory()
                .registerSubtype( MoneyReward.class )
                .registerSubtype( MultiItemReward.class )
                .registerSubtype( SingleItemReward.class );
    }

}
