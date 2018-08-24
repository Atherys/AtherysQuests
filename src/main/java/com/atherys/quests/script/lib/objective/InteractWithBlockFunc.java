package com.atherys.quests.script.lib.objective;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.quest.objective.Objectives;
import com.atherys.script.api.util.TriFunction;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Arrays;
import java.util.function.BiFunction;

public class InteractWithBlockFunc implements TriFunction<BlockState, Location<World>, ItemStack[], Objective> {
    @Override
    public Objective apply(BlockState blockState, Location<World> location, ItemStack[] items) {
        if(items == null) {
            return Objectives.blockInteract(blockState.snapshotFor(location));
        } else {
            return Objectives.blockInteractItems(blockState.snapshotFor(location), Arrays.asList(items));
        }
    }
}
