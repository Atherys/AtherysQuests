package com.atherys.quests.gson;

import com.atherys.core.gson.TypeAdapterFactoryRegistry;
import com.atherys.core.utils.RuntimeTypeAdapterFactory;
import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.events.AtherysQuestsGsonBuildEvent;
import com.atherys.quests.quest.DeliverableSimpleQuest;
import com.atherys.quests.quest.DeliverableStagedQuest;
import com.atherys.quests.quest.SimpleQuest;
import com.atherys.quests.quest.StagedQuest;
import com.atherys.quests.quest.objective.DialogObjective;
import com.atherys.quests.quest.objective.InteractWithBlockObjective;
import com.atherys.quests.quest.objective.KillEntityObjective;
import com.atherys.quests.quest.objective.ReachLocationObjective;
import com.atherys.quests.quest.requirement.*;
import com.atherys.quests.quest.reward.MoneyReward;
import com.atherys.quests.quest.reward.SingleItemReward;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.spongepowered.api.Sponge;

public class AtherysQuestsRegistry extends TypeAdapterFactoryRegistry {

    private static AtherysQuestsRegistry instance = new AtherysQuestsRegistry();

    private Gson gson;

    private AtherysQuestsRegistry() {
        add(Quest.class, RuntimeTypeAdapterFactory.of(Quest.class, "questType"));
        add(Objective.class, RuntimeTypeAdapterFactory.of(Objective.class, "objectiveType"));
        add(Requirement.class, RuntimeTypeAdapterFactory.of(Requirement.class, "requirementType"));
        add(Reward.class, RuntimeTypeAdapterFactory.of(Reward.class, "rewardType"));

        registerSubtype(Quest.class, SimpleQuest.class);
        registerSubtype(Quest.class, StagedQuest.class);
        registerSubtype(Quest.class, DeliverableSimpleQuest.class);
        registerSubtype(Quest.class, DeliverableStagedQuest.class);

        registerSubtype(Requirement.class, AndRequirement.class);
        registerSubtype(Requirement.class, OrRequirement.class);
        registerSubtype(Requirement.class, NotRequirement.class);
        registerSubtype(Requirement.class, LevelRequirement.class);
        registerSubtype(Requirement.class, MoneyRequirement.class);
        registerSubtype(Requirement.class, QuestRequirement.class);

        registerSubtype(Objective.class, KillEntityObjective.class);
        registerSubtype(Objective.class, DialogObjective.class);
        registerSubtype(Objective.class, ReachLocationObjective.class);
        registerSubtype(Objective.class, InteractWithBlockObjective.class);

        registerSubtype(Reward.class, MoneyReward.class);
        registerSubtype(Reward.class, SingleItemReward.class);
    }

    public static AtherysQuestsRegistry getInstance() {
        return instance;
    }

    public Gson getGson() {
        if (gson != null) return gson;

        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();

        AtherysQuestsGsonBuildEvent event = new AtherysQuestsGsonBuildEvent(builder);
        Sponge.getEventManager().post(event);

        registerAll(builder);

        this.gson = builder.create();
        return gson;
    }

}
