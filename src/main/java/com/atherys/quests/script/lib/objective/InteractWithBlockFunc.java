package com.atherys.quests.script.lib.objective;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.quest.objective.Objectives;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.function.BiFunction;

/**
 * @jsfunc
 */ 
public class InteractWithBlockFunc implements BiFunction<BlockState, Location<World>, Objective> {
    /**
     * An objective that requires interacting with a specific block.
     */
    @Override
    public Objective apply(BlockState blockState, Location<World> location) {
        return Objectives.blockInteract(blockState.snapshotFor(location));
    }
}
