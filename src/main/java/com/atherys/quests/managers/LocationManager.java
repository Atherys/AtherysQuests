package com.atherys.quests.managers;

import com.atherys.core.database.api.DBObject;
import com.atherys.core.database.mongo.AbstractMongoDatabaseManager;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.db.QuestsDatabase;
import org.bson.Document;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;
import java.util.UUID;

public final class LocationManager extends AbstractMongoDatabaseManager<LocationManager.QuestLocation> {

    protected LocationManager () {
        super( AtherysQuests.getInstance().getLogger(), QuestsDatabase.getInstance(), "questLocations" );
    }

    @Override
    protected Optional<Document> toDocument ( QuestLocation questLocation ) {
        return Optional.empty();
    }

    @Override
    protected Optional<QuestLocation> fromDocument ( Document document ) {
        return Optional.empty();
    }

    public static class QuestLocation implements DBObject {

        private UUID uuid;

        private Location<World> location;
        private double radius;

        private String questId;

        private QuestLocation ( Location<World> location, Quest quest, double radius ) {
            this.uuid = UUID.randomUUID();
            this.location = location;
            this.radius = radius;
            this.questId = quest.getId();
        }

        @Override
        public UUID getUUID () {
            return uuid;
        }
    }

}
