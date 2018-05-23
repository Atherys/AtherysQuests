package com.atherys.quests.managers;

import com.atherys.core.database.api.DBObject;
import com.atherys.core.database.mongo.MorphiaDatabaseManager;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.db.QuestsDatabase;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;
import java.util.UUID;

public final class LocationManager extends MorphiaDatabaseManager<LocationManager.QuestLocation> {

    private static LocationManager instance = new LocationManager();

    private LocationManager() {
        super(QuestsDatabase.getInstance(), AtherysQuests.getInstance().getLogger(), QuestLocation.class);
    }

    public static LocationManager getInstance() {
        return instance;
    }

    public Optional<QuestLocation> getByLocation(Location<World> location) {
        for (QuestLocation ql : getCache().values()) {
            if (ql.contains(location)) {
                return Optional.of(ql);
            }
        }
        return Optional.empty();
    }

    public void saveAll() {
        saveAll(getCache().values());
    }

    public boolean addQuestLocation(Location<World> location, String questId, double radius) {
        Optional<QuestLocation> questLocation = QuestManager.getInstance().getQuest(questId).map(quest -> new QuestLocation(location, quest, radius));

        if (questLocation.isPresent()) {
            QuestLocation questLoc = questLocation.get();

            for (QuestLocation ql : this.getCache().values()) {
                if (questLoc.overlaps(ql)) return false;
            }

            this.save(questLoc);

            return true;
        } else return false;
    }

    @Entity
    public static class QuestLocation implements DBObject {

        @Id
        private UUID uuid;

        private Location<World> location;
        private double radius;

        private String questId;

        private QuestLocation(Location<World> location, Quest quest, double radius) {
            this.uuid = UUID.randomUUID();
            this.location = location;
            this.radius = radius;
            this.questId = quest.getId();
        }

        private boolean overlaps(QuestLocation questLocation) {
            return (this.location.getPosition().distanceSquared(questLocation.location.getPosition())
                    < this.radius + questLocation.radius);
        }

        private boolean contains(Location<World> loc) {
            if (loc.getExtent().equals(location.getExtent())) {
                return (loc.getPosition().distance(location.getPosition()) <= radius);
            } else return false;
        }

        public double getRadius() {
            return radius;
        }

        public Location getLocation() {
            return location;
        }

        public String getQuestId() {
            return questId;
        }

        @Override
        public UUID getUUID() {
            return uuid;
        }
    }
}