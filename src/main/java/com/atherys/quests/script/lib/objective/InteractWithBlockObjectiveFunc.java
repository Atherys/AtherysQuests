package com.atherys.quests.script.lib.objective;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.quest.objective.Objectives;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.function.Function;

/**
 * @jsfunc
 */
public class InteractWithBlockObjectiveFunc implements Function<Location<World>, Objective> {
    /**
     * An objective that requires interacting with a specific block.
     *
     * @param location The block the player has to interact with.
     * @jsname interactWithBlockObjective
     */
    @Override
    public Objective apply(Location<World> location) {
        return Objectives.blockInteract(location.createSnapshot());
    }
}
