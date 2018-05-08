package com.atherys.quests.managers;

import com.atherys.core.database.api.DBObject;
import com.atherys.core.database.mongo.AbstractMongoDatabaseManager;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.db.QuestsDatabase;
import com.flowpowered.math.vector.Vector3d;
import org.bson.Document;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class LocationManager extends AbstractMongoDatabaseManager<LocationManager.QuestLocation> {

    private static LocationManager instance = new LocationManager();

    private LocationManager() {
        super(AtherysQuests.getInstance().getLogger(), QuestsDatabase.getInstance(), "questLocations");
    }

    @Override
    protected Optional<Document> toDocument(QuestLocation questLocation) {
        return Optional.empty();
    }

    public Optional<QuestLocation> getByLocation(Location<World> location){
        for(QuestLocation loc : getCache().values()){
            if(loc.contains(location)){
                return Optional.of(loc);
            }
        }
        return Optional.empty();
    }

    public static LocationManager getInstance(){
        return instance;
    }

    public void addLocationQuest(Location<World> location, String questId, double radius){
        QuestManager.getInstance().getQuest(questId).ifPresent(quest -> {
            QuestLocation questLocation = new QuestLocation(location, quest, radius);
            this.save(questLocation);
        });

    }

    @Override
    protected Optional<QuestLocation> fromDocument(Document document) {
        return Optional.empty();
    }

    public static class QuestLocation implements DBObject {

        private UUID uuid;

        private Location<World> location;
        private double radius;

        private String questId;

        private boolean contains(Location<World> loc){
            if(loc.getExtent().equals(location.getExtent())){
                return(loc.getPosition().distance(location.getPosition()) <= radius);
            } else return false;
        }

        private QuestLocation(Location<World> location, Quest quest, double radius) {
            this.uuid = UUID.randomUUID();
            this.location = location;
            this.radius = radius;
            this.questId = quest.getId();
        }

        public String getQuestId(){
            return questId;
        }
        @Override
        public UUID getUUID() {
            return uuid;
        }
    }

}
