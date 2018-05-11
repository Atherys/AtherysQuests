package com.atherys.quests.managers;

import com.atherys.core.database.api.DBObject;
import com.atherys.core.database.mongo.AbstractMongoDatabaseManager;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.db.QuestsDatabase;
import com.atherys.quests.util.DbUtils;
import com.atherys.quests.util.GsonUtils;
import com.google.gson.Gson;
import org.bson.Document;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;
import java.util.UUID;

public final class LocationManager extends AbstractMongoDatabaseManager<LocationManager.QuestLocation> {

    private static LocationManager instance = new LocationManager();

    private Gson gson = GsonUtils.getGson();

    private LocationManager() {
        super(AtherysQuests.getInstance().getLogger(), QuestsDatabase.getInstance(), "questLocations");
    }

    public Optional<QuestLocation> getByLocation(Location<World> location){
        for(QuestLocation ql : getCache().values()){
            if(ql.contains(location)){
                return Optional.of(ql);
            }
        }
        return Optional.empty();
    }

    public static LocationManager getInstance(){
        return instance;
    }

    public void saveAll(){
       saveAll(getCache().values());
    }

    public boolean addQuestLocation(Location<World> location, String questId, double radius){
        Optional<QuestLocation> questLocation = QuestManager.getInstance().getQuest(questId).map(quest ->
            new QuestLocation(location, quest, radius));

        if(questLocation.isPresent()){
            QuestLocation questLoc = questLocation.get();
            for(QuestLocation ql : this.getCache().values()){
                if(questLoc.overlaps(ql)) return false;
            }
            this.save(questLoc);
            return true;
        } else return false;
    }

    @Override
    protected Optional<Document> toDocument(QuestLocation questLocation) {
        Document document = new Document();

        Document location = DbUtils.Serialize.location(questLocation.location);
        document.append("location", location);

        document.append("questId", questLocation.questId);
        document.append("radius", questLocation.radius);

        return Optional.of(document);
    }

    @Override
    protected Optional<QuestLocation> fromDocument(Document document) {
        Optional<Location<World>> location = DbUtils.Deserialize.location((Document) document.get("location"));
        Optional<Quest> quest = QuestManager.getInstance().getQuest(document.getString("questId"));

        if(quest.isPresent() && location.isPresent()){
            return Optional.of(new QuestLocation(location.get(), quest.get(), document.getDouble("radius")));
        } else return Optional.empty();
    }

    public static class QuestLocation implements DBObject {

        private UUID uuid;

        private Location<World> location;
        private double radius;

        private String questId;

        private boolean overlaps(QuestLocation questLocation){
            return (this.location.getPosition().distanceSquared(questLocation.location.getPosition())
                    < this.radius + questLocation.radius);
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