package com.atherys.quests.gson;

import com.atherys.core.gson.TypeAdapterFactoryRegistry;
import com.atherys.core.utils.RuntimeTypeAdapterFactory;
import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.event.AtherysQuestsGsonBuildEvent;
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
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;

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
                StagedQuest.class,
                DeliverableSimpleQuest.class,
                DeliverableStagedQuest.class
        ));

        registerSubtypes(Requirement.class, Arrays.asList(
                AndRequirement.class,
                OrRequirement.class,
                NotRequirement.class,
                LevelRequirement.class,
                MoneyRequirement.class,
                QuestCompleteRequirement.class
        ));

        registerSubtypes(Objective.class, Arrays.asList(
                KillEntityObjective.class,
                DialogObjective.class,
                ReachLocationObjective.class,
                InteractWithBlockObjective.class
        ));

        registerSubtypes(Reward.class, Arrays.asList(
                MoneyReward.class,
                SingleItemReward.class
        ));
    }

    public Gson getGson() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();

        AtherysQuestsGsonBuildEvent event = new AtherysQuestsGsonBuildEvent(builder);
        Sponge.getEventManager().post(event);

        registerAll(builder);

        return builder.create();
    }

}
