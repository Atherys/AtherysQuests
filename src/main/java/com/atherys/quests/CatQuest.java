package com.atherys.quests;

import com.atherys.quests.quest.Stage;
import com.atherys.quests.quest.StagedQuest;
import com.atherys.quests.quest.objective.Objectives;
import com.atherys.quests.quest.reward.Rewards;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.Collections;
import java.util.UUID;

public class CatQuest {
    public static class Simple extends StagedQuest {

        protected Simple() {
            super("find-illidon-cat", Text.of("The Lost Cat"), Text.of("Illidon has tasked you to find his lost cat, Captain Fluffers!"), 1);
            ItemStack item = ItemStack.builder().itemType(ItemTypes.CHEST).build();
            item.offer(Keys.DISPLAY_NAME, Text.of("Lost Cat"));
            addStage(new Stage(Objectives.reachLocation(Text.of("Find Illidon's lost cat!"), Sponge.getServer().getWorld("world").get().getLocation(6290, 30, 376), 5), Collections.singletonList(Rewards.item(item))));
            addStage(new Stage(Objectives.dialog("kitty-dialog", 0, Text.of("Approach the cat")), Collections.singletonList(Rewards.item(item))));
            addStage(new Stage(Objectives.itemDelivery(item.createSnapshot(), UUID.fromString("05a88348-44c6-47c4-8cef-6bbaec99f139"), Text.of("Illidon"))));
        }
    }
}
