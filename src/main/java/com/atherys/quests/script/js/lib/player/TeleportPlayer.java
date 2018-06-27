package com.atherys.quests.script.js.lib.player;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.entity.teleport.TeleportTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.function.BiFunction;
import java.util.function.Function;

public class TeleportPlayer implements BiFunction<Player, Location<World>, Boolean> {
    @Override
    public Boolean apply(Player player, Location<World> location) {
        return player.setLocation(location);
    }
}
