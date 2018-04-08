package com.atherys.kotlin.quests

import com.atherys.quests.api.quest.Quest
import com.atherys.quests.dialog.tree.DialogNode
import com.atherys.quests.dialog.tree.DialogTree
import com.atherys.quests.quest.Stage
import com.atherys.quests.quest.objective.Objectives
import com.atherys.quests.quest.requirement.Requirements
import com.atherys.quests.quest.reward.Rewards
import com.atherys.quests.quest.task.Tasks
import com.atherys.quests.quester.Quester
import com.flowpowered.math.vector.Vector3d
import org.spongepowered.api.Sponge
import org.spongepowered.api.data.key.Keys
import org.spongepowered.api.entity.EntitySnapshot
import org.spongepowered.api.entity.EntityTypes
import org.spongepowered.api.item.ItemTypes
import org.spongepowered.api.item.enchantment.Enchantment
import org.spongepowered.api.item.enchantment.EnchantmentTypes
import org.spongepowered.api.item.inventory.ItemStack
import org.spongepowered.api.text.Text
import org.spongepowered.api.text.format.TextColors
import org.spongepowered.api.text.format.TextStyles
import org.spongepowered.api.world.Location
import java.util.Arrays.asList


class DummyQuest {

    companion object {
        fun dialog(quest: Quest<*>): DialogTree {

            val root = DialogNode.builder(0)
                    .npc(Text.of("Hello, weary traveller!"))
                    .responses(
                            DialogNode.builder(1)
                                    .player(Text.of("Hello, Merchant! Have you any work for me today?"))
                                    .npc(
                                            Text.of("You know, I", TextStyles.ITALIC, " may", TextStyles.RESET, " actually have something you'd be", TextColors.DARK_BLUE, " interested in."),
                                            Text.of("Recently, the outskirts of the town have been getting ravaged by some very nasty creatures."),
                                            Text.of("I've heard that the King himself wishes this situation to be dealt with swiftly. There may even be a reward for the one who does it.")
                                    )
                                    .responses(
                                            DialogNode.builder(10)
                                                    .player(Text.of("Well, sign me up! I love me a bit of danger."))
                                                    .npc(Text.of("Oh, excellent! Just remember who tipped you off, when the time comes for the King to reward you, eh? ;)"))
                                                    .quest(quest)
                                                    .build(),
                                            DialogNode.builder(11)
                                                    .player(Text.of("Oh, no, I'm not in the murdering vibe today. Perhaps another day."))
                                                    .npc(Text.of("Well, I wouldn't take too long if I were you. Someone else is bound to pick up on that reward."))
                                                    .build()
                                    )
                                    .build(),
                            DialogNode.builder(2)
                                    .player(Text.of("I have no time for chit-chat. Goodbye."))
                                    .build()
                    )
                    .build()

            return DialogTree.builder("merchantDialog").root(root).build()

        }
    }

    class Simple() : SimpleQuest("simpleDummyQuest", 1) {
        init {
            this name Text.of("Dummy Quest")

            this description Text.of("A dummy quest to prove that SimpleQuest works. Kill some things, reach a point, and get a magical anvil!")

            val world = Sponge.getServer().getWorld(Sponge.getServer().defaultWorldName)

            this objectives arrayOf(
                    Objectives.killEntity("Speshul Zombie", 4),
                    Objectives.killEntity("Speshul Creepa", 3),
                    Objectives.reachLocation(Text.of("a very high place"), Location( world.get(), Vector3d.from(500.0, 130.0, 600.0) ), 5.0)
            )

            this reward Rewards.item(
                    ItemStack.builder()
                            .itemType(ItemTypes.ANVIL)
                            .quantity(1)
                            .add(Keys.DISPLAY_NAME, Text.of("The Magical Anvil"))
                            .add(Keys.ITEM_ENCHANTMENTS, asList<Enchantment>(
                                    Enchantment.of(EnchantmentTypes.FLAME, 10),
                                    Enchantment.of(EnchantmentTypes.INFINITY, 4)
                            )
                            )
                            .add(Keys.ITEM_LORE, asList<Text>(
                                    Text.of("An anvil so magical, it will"),
                                    Text.of("actually set you on fire."),
                                    Text.of("For infinity.")
                            )
                            )
                            .build()
            )

        }

        override fun pickUp(quester: Quester) {
            super.pickUp(quester)

            val speshulZombie = EntitySnapshot.builder()
                    .type(EntityTypes.ZOMBIE)
                    .add(Keys.DISPLAY_NAME, Text.of("Speshul Zombie") )
                    .build()

            Tasks.spawnEntitiesInRadius(quester.cachedPlayer!!.location, 15.0, speshulZombie, 4)

            val speshulCreepa = EntitySnapshot.builder()
                    .type(EntityTypes.CREEPER)
                    .add(Keys.DISPLAY_NAME, Text.of("Speshul Creepa"))
                    .build()

            Tasks.spawnEntitiesInRadius(quester.cachedPlayer!!.location, 15.0, speshulCreepa, 3)
        }
    }

    class Staged() : StagedQuest("stagedDummyQuest", 1) {

        init {
            this name Text.of("Staged Dummy Quest")

            this description Text.of("The staged version of the dummy quest. Kill some things, one type at a time, and then reach a point. Also, you still get a magical anvil.")

            this require Requirements.level(12)

            val world = Sponge.getServer().getWorld(Sponge.getServer().defaultWorldName)

            this stages arrayOf(
                    Stage(Objectives.killEntity("Speshul Zombie", 4)),
                    Stage(Objectives.killEntity("Speshul Creepa", 3)),
                    Stage(Objectives.reachLocation(Text.of("a very high place"), Location(world.get(), Vector3d.from(500.0, 130.0, 600.0)), 5.0))
            )

            this reward Rewards.item(
                    ItemStack.builder()
                            .itemType(ItemTypes.ANVIL)
                            .quantity(1)
                            .add(Keys.DISPLAY_NAME, Text.of("The Magical Anvil"))
                            .add(Keys.ITEM_ENCHANTMENTS, asList<Enchantment>(
                                    Enchantment.of(EnchantmentTypes.FLAME, 10),
                                    Enchantment.of(EnchantmentTypes.INFINITY, 4)
                            )
                            )
                            .add(Keys.ITEM_LORE, asList<Text>(
                                    Text.of("An anvil so magical, it will"),
                                    Text.of("actually set you on fire."),
                                    Text.of("For infinity.")
                            )
                            )
                            .build()
            )
        }

        override fun pickUp(quester: Quester) {
            super.pickUp(quester)

            val speshulZombie = EntitySnapshot.builder()
                    .type(EntityTypes.ZOMBIE)
                    .add(Keys.DISPLAY_NAME, Text.of("Speshul Zombie") )
                    .build()

            Tasks.spawnEntitiesInRadius(quester.cachedPlayer!!.location, 15.0, speshulZombie, 4)

            val speshulCreepa = EntitySnapshot.builder()
                    .type(EntityTypes.CREEPER)
                    .add(Keys.DISPLAY_NAME, Text.of("Speshul Creepa"))
                    .build()

            Tasks.spawnEntitiesInRadius(quester.cachedPlayer!!.location, 15.0, speshulCreepa, 3)
        }

    }

}
