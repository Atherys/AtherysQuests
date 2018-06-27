package com.atherys.quests.script.js.lib.location;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.World;

import java.util.function.Function;

public class GetWorldFromName implements Function<String, World> {
    @Override
    public World apply(String worldName) {
        return Sponge.getServer().getWorld(worldName).orElse(null);
    }
}
