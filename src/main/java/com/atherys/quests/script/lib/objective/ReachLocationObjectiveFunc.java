package com.atherys.quests.script.lib.objective;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.quest.objective.Objectives;
import com.atherys.script.api.util.TriFunction;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class ReachLocationObjectiveFunc implements TriFunction<Location<World>, Text, Double, Objective> {

    @Override
    public Objective apply(Location<World> location, Text name, Double radius) {
        return Objectives.reachLocation(name, location, radius);
    }
}
