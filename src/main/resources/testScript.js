var QUEST_NAME = textOf("Test", color("RED"), style("BOLD"), "Quest", style("RESET"));
var QUEST_DESCRIPTION = textOf("A test quest to see if everything works. Kill 3 zombies and 4 creepers.");

function getQuestType() {
    return QuestTypes.SIMPLE;
}

function onLoad(quest) {
    quest.setName(QUEST_NAME);
    quest.setDescription(QUEST_DESCRIPTION);

    quest.addObjectives(
        createKillEntityObjective("zombie", 3),
        createKillEntityObjective("creeper", 4)
    );

    var rewardItem = createItemStack("minecraft:anvil", 1);
    setItemStackDisplayName(item, textOf("An Anvil"));
    setItemStackLore(item,
        textOf("Line 1"),
        textOf("Line 2")
    );
    setItemStackEnchants(item,
        enchantmentOf("minecraft:thorns", 5),
        enchantmentOf("minecraft:looting", 6)
    );

    quest.addRewards(
        createItemReward(rewardItem),
        createCurrencyReward(50.0, "atherys:argent")
    );
}

function onPickUp(quest, quester) {

}

function onProgress(quest, quester) {

}

function onComplete(quest, quester) {

}

function onTurnIn(quest, quester) {

}