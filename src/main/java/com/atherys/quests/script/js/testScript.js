var QUEST_NAME = Text.of("Test", TextColors.RED, "Quest");
var QUEST_DESCRIPTION = Text.of("A test quest to see if everything works. Kill 3 zombies and 4 creepers.");

function onLoad(quest) {
    quest.setName(QUEST_NAME);
    quest.setDescription(QUEST_DESCRIPTION);

    quest.addObjectives(
        Objectives.killEntity("zombie", 3),
        Objectives.killEntity("creeper", 4)
    );

    var item = sponge.createItem("minecraft:anvil", 1);
    sponge.addItemEnchantments(item,
        sponge.enchantment("minecraft:thorns", 5),
        sponge.enchantment("minecraft:looting", 6)
    );

    quest.addRewards(
        Rewards.item(item),
        Rewards.currency(50.0, "atherys:argent")
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