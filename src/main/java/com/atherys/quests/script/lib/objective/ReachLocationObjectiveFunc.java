package com.atherys.quests.script.lib.objective;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.quest.objective.Objectives;
import com.atherys.script.api.function.ScriptTriFunction;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * @jsfunc
 */
public class ReachLocationObjectiveFunc implements ScriptTriFunction<Location<World>, Text, Double, Objective> {
    /**
     * An objective that requires reaching a specific location, within a radius.
     *
     * @param location The location to reach.
     * @param name     The name of the location.
     * @param radius   The radius around the location.
     * @jsname reachLocationObjective
     */
    @Override
    public Objective call(Location<World> location, Text name, Double radius) {
        return Objectives.reachLocation(name, location, radius);
    }
}
