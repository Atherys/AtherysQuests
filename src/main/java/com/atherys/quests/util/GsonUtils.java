package com.atherys.quests.util;

import com.atherys.core.gson.ConfigurateAdapter;
import com.atherys.core.utils.RuntimeTypeAdapterFactory;
import com.atherys.quests.quest.objective.DialogObjective;
import com.atherys.quests.quest.objective.KillEntityObjective;
import com.atherys.quests.quest.objective.Objective;
import com.atherys.quests.quest.requirement.*;
import com.atherys.quests.quest.reward.MoneyReward;
import com.atherys.quests.quest.reward.MultiItemReward;
import com.atherys.quests.quest.reward.Reward;
import com.atherys.quests.quest.reward.SingleItemReward;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;

public final class GsonUtils {

    private static RuntimeTypeAdapterFactory<Objective> objectiveTypeAdapterFactory = RuntimeTypeAdapterFactory.of( Objective.class, "__type__" )
            .registerSubtype( KillEntityObjective.class )
            .registerSubtype( DialogObjective.class );

    private static RuntimeTypeAdapterFactory<Reward> rewardRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of( Reward.class, "__type__" )
            .registerSubtype( MoneyReward.class )
            .registerSubtype( MultiItemReward.class )
            .registerSubtype( SingleItemReward.class );

    private static RuntimeTypeAdapterFactory<Requirement> requirementRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of( Requirement.class, "__type__" )
            .registerSubtype( LevelRequirement.class )
            .registerSubtype( MoneyRequirement.class )
            .registerSubtype( QuestRequirement.class )
            .registerSubtype( OrRequirement.class )
            .registerSubtype( AndRequirement.class );

    public static Gson getGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter( Text.class, new CompactTextAdapter() )
                .registerTypeAdapter( ItemStackSnapshot.class, new ConfigurateAdapter<>( ItemStackSnapshot.class ) )
                .registerTypeAdapter( Currency.class, new ConfigurateAdapter<>( Currency.class ) )
                .registerTypeAdapterFactory( objectiveTypeAdapterFactory )
                .registerTypeAdapterFactory( rewardRuntimeTypeAdapterFactory )
                .registerTypeAdapterFactory( requirementRuntimeTypeAdapterFactory )
                .create();
    }

}
