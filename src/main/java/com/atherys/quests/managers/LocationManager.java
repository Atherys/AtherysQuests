package com.atherys.quests.managers;

import com.atherys.core.database.api.DBObject;
import com.atherys.core.database.mongo.AbstractMongoDatabaseManager;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.db.QuestsDatabase;
import com.atherys.quests.util.GsonUtils;
import com.google.gson.Gson;
import org.bson.Document;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;
import java.util.UUID;

public final class LocationManager extends AbstractMongoDatabaseManager<LocationManager.QuestLocation> {

    private static LocationManager instance = new LocationManager();
    private static Gson gson = GsonUtils.getGson();

    private LocationManager() {
        super(AtherysQuests.getInstance().getLogger(), QuestsDatabase.getInstance(), "questLocations");
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

    public void saveAll(){
       saveAll(this.getCache().values());
    }

    public boolean addLocationQuest(Location<World> location, String questId, double radius){
        Optional<QuestLocation> questLocation = QuestManager.getInstance().getQuest(questId).map(quest ->
            new QuestLocation(location, quest, radius));
        
        if(questLocation.isPresent()){
            for(QuestLocation ql : this.getCache().values()){
                if(questLocation.get().overlaps(ql)) return false;
            }
            this.save(questLocation.get());
            return true;

        } else return false;
    }

    @Override protected Optional<Document> toDocument(QuestLocation questLocation) {
        return Optional.empty();
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

        private boolean overlaps(QuestLocation questLocation){
            return (this.location.getPosition().distance(questLocation.location.getPosition()) < this.radius + questLocation.radius);
        }

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
