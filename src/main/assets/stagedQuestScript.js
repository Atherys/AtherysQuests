function onStart() {
    // define the very basic elements of the quest
    var QUEST_ID = "test-staged-quest";
    var QUEST_NAME = textOf(["Test", color("RED"), " Staged Quest"]);
    var QUEST_DESCRIPTION = textOf(["A test quest to ensure all is well. Kill 5 zombies and THEN reach some location. You know, for testing."]);
    var VERSION = 1;

    // create the quest
    var quest = createSimpleQuest(QUEST_ID, QUEST_NAME, QUEST_DESCRIPTION, VERSION);

    // Add a requirement for currency. The player must have this many of a currency with id "atherys:argent" in order to pick up the quest
    addQuestRequirement(quest, currencyRequirement(100.0, "atherys:argent"));
    // Add a requirement for reputation. The player must have this much reputation with a faction with id "tester-faction"
    addQuestRequirement(quest, reputationRequirement( getFactionFromId("tester-faction"), 1000));

    // Since this is a staged quest, create a stage with a killEntityObjective for the first stage of the quest.
    // Upon completion, this stage will reward the player with 10 argents.
    var killZombiesStage = stageOf( killEntityObjective("zombie", 5), [
            currencyReward(10.0, "atherys:argent")
        ]
    );

    var reachLocationStage = stageOf( reachLocationObjective( locationOf( getWorldFromName("world"), 600.0, 78, 900.0) ), [] );

    // add the stages to the quest
    addQuestStages( quest, [
        killZombiesStage,
        reachLocationStage
    ]);

    // create an item reward
    var itemReward = createItemStack("minecraft:anvil", 7);
    setItemStackDisplayName( itemReward, textOf(["Anvil Reward"]) );

    // add rewards
    addQuestReward(quest, currencyReward(100.0, "atherys:argent"));
    addQuestRewards(quest, [
        reputationReward( getFactionFromId("tester-faction"), 100 ),
        itemStackReward( itemReward );
    ]);

    return quest;
}

function onPickUp(quester, quest) {
}

function onBegin(quester, quest) {
}

function onProgress(quester, quest, objective) {
}

function onComplete(quester, quest) {
}

function onTurnIn(quester, quest) {
}