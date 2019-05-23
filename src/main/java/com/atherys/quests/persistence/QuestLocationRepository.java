package com.atherys.quests.persistence;

import com.atherys.core.db.CachedHibernateRepository;
import com.atherys.quests.entity.QuestLocation;
import com.google.inject.Singleton;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

@Singleton
public class QuestLocationRepository extends CachedHibernateRepository<QuestLocation, Long> {
    QuestLocationRepository() {
        super(QuestLocation.class);
    }

    public Optional<QuestLocation> getQuestLocationFromQuestLocation(QuestLocation location) {
        return cache.findOne(ql -> checkOverlap(ql, location));
    }

    public Optional<QuestLocation> getQuestLocationFromBlock(Location<World> location) {
        return cache.findOne(ql -> checkSameBlock(ql.getLocation(), location));
    }

    /**
     * Check if Quest Location A overlaps with Quest Location B
     *
     * @param qlA Quest Location A
     * @param qlB Quest Location B
     * @return Whether they overlap or not
     */
    public boolean checkOverlap(QuestLocation qlA, QuestLocation qlB) {
        return qlA.getLocation().getExtent().equals(qlB.getLocation().getExtent())
                && (qlA.getLocation().getPosition().distance(qlB.getLocation().getPosition()) < Math.pow(qlA.getRadius() + qlB.getRadius(), 2));
    }

    /**
     * Check if Location A and Location B are on the same block
     *
     * @param locA Location A
     * @param locB Location B
     * @return Whether they are the same block
     */
    public boolean checkSameBlock(Location<World> locA, Location<World> locB) {
        return locA.getExtent().equals(locB.getExtent()) && locA.getBlockPosition().equals(locB.getBlockPosition());
    }
}