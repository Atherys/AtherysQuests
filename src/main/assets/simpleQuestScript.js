function onStart() {
    // define the very basic elements of the quest
    var QUEST_ID = "test-simple-quest";
    var QUEST_NAME = textOf(["Test", color("RED"), " Simple Quest"]);
    var QUEST_DESCRIPTION = textOf(["A test quest to ensure all is well. Kill 5 zombies and reach some location. You know, for testing."]);
    var VERSION = 1;

    // create the quest
    var quest = createSimpleQuest(QUEST_ID, QUEST_NAME, QUEST_DESCRIPTION, VERSION);

    addQuestRequirement(quest, currencyRequirement(100.0, "atherys:argent"));
    addQuestRequirement(quest, reputationRequirement("tester-faction", 1000));

    addQuestObjectives(quest, [
        killEntityObjective("zombie", 5),
        reachLocationObjective( locationOf( getWorldFromName("world"), 600.0, 78, 900.0) )
    ]);

    var itemReward = createItemStack("minecraft:anvil", 7);
    setItemStackDisplayName( itemReward, textOf(["Anvil Reward"]) );

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