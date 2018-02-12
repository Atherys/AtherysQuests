package com.atherys.quests.util;

import com.atherys.quests.quest.objective.DialogObjective;
import com.atherys.quests.quest.objective.KillEntityObjective;
import com.atherys.quests.quest.objective.Objective;
import com.atherys.quests.quest.requirement.LevelRequirement;
import com.atherys.quests.quest.requirement.MoneyRequirement;
import com.atherys.quests.quest.requirement.QuestRequirement;
import com.atherys.quests.quest.requirement.Requirement;
import com.atherys.quests.quest.reward.MoneyReward;
import com.atherys.quests.quest.reward.MultiItemReward;
import com.atherys.quests.quest.reward.Reward;
import com.atherys.quests.quest.reward.SingleItemReward;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class GsonUtils {

    private static RuntimeTypeAdapterFactory<Objective> objectiveFactory =
            RuntimeTypeAdapterFactory.of(Objective.class)
            .registerSubtype(KillEntityObjective.class, "KillEntity")
            .registerSubtype(DialogObjective.class, "Dialog");

    private static RuntimeTypeAdapterFactory<Reward> rewardFactory =
            RuntimeTypeAdapterFactory.of(Reward.class)
            .registerSubtype(MoneyReward.class, "Money")
            .registerSubtype(MultiItemReward.class, "MultiItem")
            .registerSubtype(SingleItemReward.class, "SingleItem");


    private static RuntimeTypeAdapterFactory<Requirement> requirementFactory =
            RuntimeTypeAdapterFactory.of(Requirement.class)
            .registerSubtype(LevelRequirement.class, "Level")
            .registerSubtype(QuestRequirement.class, "Quest")
            .registerSubtype(MoneyRequirement.class, "Money");

    public static Gson getNewQuestsGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapterFactory( objectiveFactory )
                .registerTypeAdapterFactory( requirementFactory )
                .registerTypeAdapterFactory( rewardFactory )
                .create();
    }

}
