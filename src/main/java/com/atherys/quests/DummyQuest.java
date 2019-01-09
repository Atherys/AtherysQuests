package com.atherys.quests;


import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogTree;
import com.atherys.quests.model.quest.Stage;
import com.atherys.quests.model.quest.StagedQuest;
import com.atherys.quests.model.quest.objective.Objectives;
import com.atherys.quests.model.quest.requirement.Requirements;
import com.atherys.quests.model.quest.reward.Rewards;
import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class DummyQuest {

    public static DialogTree dialog(String id, Quest quest) {
        DialogNode root = DialogNode.builder(0)
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
                .build();

        return DialogTree.builder(id).root(root).build();
    }

    public static class Staged extends StagedQuest {

        protected Staged() {
            super("simpleStagedQuest", Text.of("Simple Staged Quest"), Text.of("This is a simple staged completedQuest. Go kill some stuff."), 1);

            addRequirement(Requirements.level(12));

            addStage(new Stage(Objectives.killEntity("zombie", 6)));
            addStage(new Stage(Objectives.killEntity("creeper", 4)));
            World world = Sponge.getServer().getWorld(Sponge.getServer().getDefaultWorldName()).get();
            addStage(new Stage(Objectives.reachLocation(Text.of("a high place"), new Location<>(world, Vector3d.from(2500, 130, 4000)), 5.0)));

            addReward(Rewards.item(ItemStack.of(ItemTypes.ANVIL, 5)));
        }

    }


}
