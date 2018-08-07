package com.atherys.quests.service;

import com.atherys.core.command.annotation.Description;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.command.quest.AttachQuestCommand;
import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Color;

import java.util.concurrent.TimeUnit;

public class ParticleEmitter {
    private static ParticleEmitter instance = new ParticleEmitter();
    private static ParticleEffect effect = ParticleEffect.builder()
                .type(ParticleTypes.REDSTONE_DUST)
                .option(ParticleOptions.COLOR, Color.YELLOW)
                .velocity(Vector3d.ONE)
                .o
                .quantity(10)
                .build();
    private Task particleEmissionTask;
    private boolean isEmitting;

    public static ParticleEmitter getInstance() {
        return instance;
    }

    /**
     * Emits particles for every quest block.
     */
    private void emitParticles(){
        AtherysQuests.getLocationManager().getQuestBlocks().forEach((location, questLocation) ->{
             location.getExtent().getNearbyEntities(location.getPosition(), questLocation.getRadius()).forEach(entity -> {
                 if (entity instanceof Player){
                     ((Player) entity).spawnParticles(effect, location.getPosition(), (int) questLocation.getRadius());
                 }
             });
        });
    }

    /**
     * Starts the particle emission.
     */
    public void startEmitting(){
        particleEmissionTask = Task.builder()
                .execute(this::emitParticles)
                .interval(1, TimeUnit.SECONDS)
                .submit(AtherysQuests.getInstance());
        isEmitting = true;
    }

    public void stopEmitting(){
        particleEmissionTask.cancel();
        isEmitting = false;
    }
}
