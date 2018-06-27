package com.atherys.quests.script.js.lib.player;

import org.spongepowered.api.entity.living.player.Player;

import java.util.UUID;
import java.util.function.Function;

public class GetPlayerUUID implements Function<Player,UUID> {
    @Override
    public UUID apply(Player player) {
        return player.getUniqueId();
    }
}
