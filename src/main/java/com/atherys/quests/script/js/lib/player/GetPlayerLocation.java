package com.atherys.quests.script.js.lib.player;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.function.Function;

public class GetPlayerLocation implements Function<Player, Location<World>> {
    @Override
    public Location<World> apply(Player player) {
        return player.getLocation();
    }
}
