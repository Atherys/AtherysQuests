package com.atherys.quests.util;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.IndirectEntityDamageSource;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.EntityUniverse;

import java.util.Optional;
import java.util.UUID;

public class EntityUtils {

    public static Optional<Entity> getEntity(UUID id) {
        for (World world : Sponge.getServer().getWorlds()) {
            Optional<Entity> entity = world.getEntity(id);
            if (entity.isPresent()) {
                return entity;
            }
        }
        return Optional.empty();
    }

    public static Optional<Entity> getNonPlayerFacingEntity(Entity source, double distance) {
        return source.getWorld().getIntersectingEntities(source, distance).stream()
                .map(EntityUniverse.EntityHit::getEntity)
                .findFirst();
    }

    public static Optional<Player> playerKilledEntity(EntityDamageSource source) {
        if (source.getSource() instanceof Player) {
            return Optional.of((Player) source.getSource());
        }

        if (source instanceof IndirectEntityDamageSource) {
            IndirectEntityDamageSource indirect = (IndirectEntityDamageSource) source;
            if (indirect.getIndirectSource() instanceof Player) {
                return Optional.of((Player) indirect.getIndirectSource());
            }
        }

        return Optional.empty();
    }
}
