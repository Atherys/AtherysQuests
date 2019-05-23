package com.atherys.quests.script.lib;

import com.atherys.quests.event.dialog.DialogEndEvent;
import com.atherys.quests.event.dialog.DialogProceedEvent;
import com.atherys.quests.event.dialog.DialogRegistrationEvent;
import com.atherys.quests.event.dialog.DialogStartEvent;
import com.atherys.quests.event.quest.*;
import com.atherys.quests.script.lib.dialog.AttachDialog;
import com.atherys.quests.script.lib.dialog.CreateDialogNode;
import com.atherys.quests.script.lib.dialog.CreateDialogTree;
import com.atherys.quests.script.lib.dialog.RegisterDialogTree;
import com.atherys.quests.script.lib.objective.*;
import com.atherys.quests.script.lib.quest.*;
import com.atherys.quests.script.lib.requirement.*;
import com.atherys.quests.script.lib.reward.SingleItemRewardFunc;
import com.atherys.script.api.library.LibraryExtension;
import com.atherys.script.api.library.ScriptLibrary;
import com.atherys.script.js.library.event.EventHandlerFunction;

public class QuestExtension implements LibraryExtension {

    private static QuestExtension instance = new QuestExtension();

    private QuestExtension() {
    }

    public static QuestExtension getInstance() {
        return instance;
    }

    @Override
    public void extend(ScriptLibrary scriptLibrary) {
        scriptLibrary.put("onDialogRegistration", new EventHandlerFunction<>(DialogRegistrationEvent.class));
        scriptLibrary.put("onDialogStart", new EventHandlerFunction<>(DialogStartEvent.class));
        scriptLibrary.put("onDialogProgress", new EventHandlerFunction<>(DialogProceedEvent.class));
        scriptLibrary.put("onDialogComplete", new EventHandlerFunction<>(DialogEndEvent.class));

        scriptLibrary.put("onQuestRegistration", new EventHandlerFunction<>(QuestRegistrationEvent.class));
        scriptLibrary.put("onQuestStart", new EventHandlerFunction<>(QuestStartedEvent.class));
        scriptLibrary.put("onSimpleQuestProgress", new EventHandlerFunction<>(SimpleQuestProgressEvent.class));
        scriptLibrary.put("onStagedQuestProgress", new EventHandlerFunction<>(StagedQuestProgressEvent.class));
        scriptLibrary.put("onQuestComplete", new EventHandlerFunction<>(QuestCompletedEvent.class));
        scriptLibrary.put("onQuestTurnIn", new EventHandlerFunction<>(QuestTurnedInEvent.class));

        scriptLibrary.put("dialogNode", new CreateDialogNode());
        scriptLibrary.put("dialogTree", new CreateDialogTree());
        scriptLibrary.put("attachDialog", new AttachDialog());

        scriptLibrary.put("createSimpleQuest", new CreateSimpleQuest());
        scriptLibrary.put("createDeliverableQuest", new CreateDeliverableQuest());
        scriptLibrary.put("stageOf", new StageOf());
        scriptLibrary.put("createStagedQuest", new CreateStagedQuest());

        scriptLibrary.put("getQuestById", new GetQuestById());

        scriptLibrary.put("addQuestObjective", new AddQuestObjective());
        scriptLibrary.put("addQuestReward", new AddQuestReward());

        scriptLibrary.put("addQuestStages", new AddQuestStages());
        scriptLibrary.put("addQuestObjectives", new AddQuestObjectives());
        scriptLibrary.put("addQuestRequirements", new AddQuestRequirements());
        scriptLibrary.put("addQuestRewards", new AddQuestRewards());

        scriptLibrary.put("getQuestStages", new GetQuestStages());
        scriptLibrary.put("getQuestObjectives", new GetQuestObjectives());
        scriptLibrary.put("getQuestRequirements", new GetQuestRequirements());
        scriptLibrary.put("getQuestRewards", new GetQuestRewards());

        scriptLibrary.put("dialogObjective", new DialogObjectiveFunc());
        scriptLibrary.put("interactWithBlockObjective", new InteractWithBlockObjectiveFunc());
        scriptLibrary.put("killEntityObjective", new KillEntityObjectiveFunc());
        scriptLibrary.put("reachLocationObjective", new ReachLocationObjectiveFunc());
        scriptLibrary.put("itemDeliveryObjective", new ItemDeliveryObjectiveFunc());

        scriptLibrary.put("andRequirement", new AndRequirementFunc());
        scriptLibrary.put("orRequirement", new OrRequirementFunc());
        scriptLibrary.put("notRequirement", new NotRequirementFunc());
        scriptLibrary.put("turnedInQuestRequirement", new CompletedQuestRequirementFunc());
        scriptLibrary.put("hasQuestRequirement", new HasQuestRequirementFunc());
        scriptLibrary.put("moneyRequirement", new MoneyRequirementFunc());
        scriptLibrary.put("levelRequirement", new LevelRequirementFunc());

        scriptLibrary.put("moneyReward", new MoneyRequirementFunc());
        scriptLibrary.put("singleItemReward", new SingleItemRewardFunc());

        scriptLibrary.put("registerQuest", new RegisterQuest());
        scriptLibrary.put("registerDialogTree", new RegisterDialogTree());
    }
}
