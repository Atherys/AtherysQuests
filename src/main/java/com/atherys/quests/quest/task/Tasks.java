package com.atherys.quests.quest.task;

import com.flowpowered.math.vector.Vector3d;
import org.apache.commons.lang3.RandomUtils;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntitySnapshot;
import org.spongepowered.api.util.AABB;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * A "task" in the context of the Quests plugin is defined as any generic executive action the quest maker would like the quest to do.
 * Examples include: spawning mobs, spawning particles, adding/removing blocks from the world, etc. etc.
 * This factory class is designed to ease the creation of such tasks
 */
public final class Tasks {

    public static void spawnEntitiesInRadius( Location<World> location, double radius, EntitySnapshot snapshot, int amount ) {
        for ( int i = 0; i < amount; i++ ) {

            Vector3d position = Vector3d.from(
                    RandomUtils.nextDouble( location.getPosition().getX() - radius, location.getPosition().getX() + radius ),
                    0,
                    RandomUtils.nextDouble( location.getPosition().getZ() - radius, location.getPosition().getZ() + radius )
            );

            position.add(
                    0,
                    location.getExtent().getHighestYAt( position.getFloorX(), position.getFloorZ() ),
                    0
            );

            Entity entity = location.getExtent().createEntity( snapshot.getType(), position );
            snapshot.getValues().forEach( entity::offer );
            location.getExtent().spawnEntity( entity );
        }
    }

    public static void destroyEntitiesInBox( World world, AABB box ) {
        world.getIntersectingEntities( box ).forEach( Entity::remove );
    }

}
