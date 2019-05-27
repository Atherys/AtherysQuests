package com.atherys.quests.service;

import com.atherys.quests.AtherysQuests;
import com.flowpowered.math.vector.Vector3d;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Color;

import java.util.concurrent.TimeUnit;

@Singleton
public class ParticleService {

    private static ParticleEffect effect = ParticleEffect.builder()
            .type(ParticleTypes.REDSTONE_DUST)
            .option(ParticleOptions.COLOR, Color.YELLOW)
            .velocity(Vector3d.ONE)
            .quantity(10)
            .build();
    @Inject
    QuestLocationService questLocationService;
    private Task particleEmissionTask;

    private boolean isEmitting;

    ParticleService() {
    }

    /**
     * Emits particles for every Quest block.
     */
    private void emitParticles() {
        questLocationService.getQuestBlocks().forEach((location, questLocation) -> {
            location.getExtent().getNearbyEntities(location.getPosition(), questLocation.getRadius()).forEach(entity -> {
                if (entity instanceof Player) {
                    ((Player) entity).spawnParticles(effect, location.getPosition(), (int) questLocation.getRadius());
                }
            });
        });
    }

    /**
     * Starts the particle emission.
     */
    public void startEmitting() {
        particleEmissionTask = Task.builder()
                .execute(this::emitParticles)
                .interval(1, TimeUnit.SECONDS)
                .submit(AtherysQuests.getInstance());

        isEmitting = true;
    }

    public void stopEmitting() {
        particleEmissionTask.cancel();
        isEmitting = false;
    }

    public boolean isEmitting() {
        return isEmitting;
    }
}
