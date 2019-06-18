package com.atherys.quests.script.lib.objective;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.quest.objective.Objectives;
import com.atherys.script.api.function.ScriptFunction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * @jsfunc
 */
public class InteractWithBlockObjectiveFunc implements ScriptFunction<Location<World>, Objective> {
    /**
     * An objective that requires interacting with a specific block.
     *
     * @param location The block the player has to interact with.
     * @jsname interactWithBlockObjective
     */
    @Override
    public Objective call(Location<World> location) {
        return Objectives.blockInteract(location.createSnapshot());
    }
}
