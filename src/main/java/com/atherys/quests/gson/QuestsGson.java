package com.atherys.quests.gson;

import com.atherys.core.utils.RuntimeTypeAdapterFactory;
import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.events.AtherysQuestsGsonBuildEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import org.spongepowered.api.Sponge;

public class QuestsGson {

    private static QuestsGson instance = new QuestsGson();

    private RuntimeTypeAdapterFactory<Quest> questRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Quest.class, "questType");
    private RuntimeTypeAdapterFactory<Objective> objectiveTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Objective.class, "objectiveType");
    private RuntimeTypeAdapterFactory<Reward> rewardRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Reward.class, "rewardType");
    private RuntimeTypeAdapterFactory<Requirement> requirementRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Requirement.class, "requirementType");

    private QuestsGson() {
    }

    public Gson getGson() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();

        AtherysQuestsGsonBuildEvent event = new AtherysQuestsGsonBuildEvent(builder);
        Sponge.getEventManager().post(event);

        builder.registerTypeAdapterFactory(requirementRuntimeTypeAdapterFactory)
                .registerTypeAdapterFactory(objectiveTypeAdapterFactory)
                .registerTypeAdapterFactory(rewardRuntimeTypeAdapterFactory)
                .registerTypeAdapterFactory(questRuntimeTypeAdapterFactory);

        return builder.create();
    }

    public <T extends Quest> void registerQuestType(Class<T> quest) {
        questRuntimeTypeAdapterFactory.registerSubtype(quest);
    }

    public <T extends Requirement> void registerRequirementType(Class<T> requirement) {
        requirementRuntimeTypeAdapterFactory.registerSubtype(requirement);
    }

    public <T extends Objective> void registerObjectiveType(Class<T> objective) {
        objectiveTypeAdapterFactory.registerSubtype(objective);
    }

    public <T extends Reward> void registerRewardType(Class<T> reward) {
        rewardRuntimeTypeAdapterFactory.registerSubtype(reward);
    }

    public RuntimeTypeAdapterFactory<Quest> getQuestRuntimeTypeAdapterFactory() {
        return questRuntimeTypeAdapterFactory;
    }

    public RuntimeTypeAdapterFactory<Objective> getObjectiveTypeAdapterFactory() {
        return objectiveTypeAdapterFactory;
    }

    public RuntimeTypeAdapterFactory<Reward> getRewardRuntimeTypeAdapterFactory() {
        return rewardRuntimeTypeAdapterFactory;
    }

    public RuntimeTypeAdapterFactory<Requirement> getRequirementRuntimeTypeAdapterFactory() {
        return requirementRuntimeTypeAdapterFactory;
    }

    public static QuestsGson getInstance() {
        return instance;
    }

}
