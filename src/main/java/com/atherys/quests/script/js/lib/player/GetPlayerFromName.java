package com.atherys.quests.script.js.lib.player;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.util.function.Function;

public class GetPlayerFromName implements Function<String, Player> {
    @Override
    public Player apply(String playerName) {
        return Sponge.getServer().getPlayer(playerName).orElse(null);
    }
}
