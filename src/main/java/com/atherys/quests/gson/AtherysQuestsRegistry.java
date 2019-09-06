package com.atherys.quests.gson;

import com.atherys.core.gson.TypeAdapterFactoryRegistry;
import com.atherys.core.gson.TypeAdapters;
import com.atherys.core.utils.RuntimeTypeAdapterFactory;
import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.quest.SimpleQuest;
import com.atherys.quests.quest.StagedQuest;
import com.atherys.quests.quest.objective.*;
import com.atherys.quests.quest.requirement.*;
import com.atherys.quests.quest.reward.CommandReward;
import com.atherys.quests.quest.reward.MoneyReward;
import com.atherys.quests.quest.reward.ItemsReward;
import com.atherys.quests.util.CompactTextAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Singleton;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

import java.util.Arrays;

@Singleton
public class AtherysQuestsRegistry extends TypeAdapterFactoryRegistry {

    AtherysQuestsRegistry() {
        add(Quest.class, RuntimeTypeAdapterFactory.of(Quest.class, "questType"));
        add(Objective.class, RuntimeTypeAdapterFactory.of(Objective.class, "objectiveType"));
        add(Requirement.class, RuntimeTypeAdapterFactory.of(Requirement.class, "requirementType"));
        add(Reward.class, RuntimeTypeAdapterFactory.of(Reward.class, "rewardType"));

        registerSubtypes(Quest.class, Arrays.asList(
                SimpleQuest.class,
                StagedQuest.class
        ));

        registerSubtypes(Requirement.class, Arrays.asList(
                AndRequirement.class,
                OrRequirement.class,
                NotRequirement.class,
                LevelRequirement.class,
                MoneyRequirement.class,
                QuestTurnedInRequirement.class,
                QuestCompleteRequirement.class
        ));

        registerSubtypes(Objective.class, Arrays.asList(
                KillEntityObjective.class,
                DialogObjective.class,
                ReachLocationObjective.class,
                InteractWithBlockObjective.class,
                ItemDeliveryObjective.class
        ));

        registerSubtypes(Reward.class, Arrays.asList(
                MoneyReward.class,
                ItemsReward.class,
                CommandReward.class
        ));
    }

    public Gson getGson() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();

        TypeAdapters.registerCatalogTypes(builder,
                Currency.class);
        TypeAdapters.registerSerializables(builder,
                ItemStackSnapshot.class,
                BlockSnapshot.class,
                Location.class);
        builder.registerTypeAdapter(Text.class, new CompactTextAdapter());

        registerAll(builder);
        return builder.create();
    }
}
