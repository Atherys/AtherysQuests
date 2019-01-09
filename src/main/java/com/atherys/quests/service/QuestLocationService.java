package com.atherys.quests.service;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.QuestLocationType;
import com.atherys.quests.persistence.QuestLocationRepository;
import com.atherys.quests.model.QuestLocation;
import com.google.inject.Inject;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class QuestLocationService {

    private static QuestLocationService instance = new QuestLocationService();

    @Inject
    QuestLocationRepository repository;

    @Inject
    QuestService questService;

    private Map<Location, QuestLocation> questBlocks = new HashMap<>();

    private Map<Location, QuestLocation> questRads = new HashMap<>();

    private QuestLocationService() {
    }

    public static QuestLocationService getInstance() {
        return instance;
    }

    public Map<Location, QuestLocation> getQuestBlocks() {
        return questBlocks;
    }

    public Optional<QuestLocation> getByRadius(Location<World> location) {
        for (QuestLocation ql : questRads.values()) {
            if (ql.contains(location)) {
                return Optional.of(ql);
            }
        }
        return Optional.empty();
    }

    public Optional<QuestLocation> getByBlock(Location<World> location) {
        for (QuestLocation ql : questBlocks.values()) {
            if (ql.sameBlockAs(location)) {
                return Optional.of(ql);
            }
        }
        return Optional.empty();
    }

    /**
     * Gets the completedQuest block if there is a completedQuest block at the location, and if not tries to get a radius one.
     * @param location
     * @return
     */
    public Optional<QuestLocation> getByLocation(Location<World> location) {
        Optional<QuestLocation> questLocation = getByBlock(location);
        if (questLocation.isPresent()){
            return questLocation;
        } else {
            return getByRadius(location);
        }
    }

//    public void saveAll() {
//        saveAll(getCache().values());
//    }
//
//
//    public void loadAll(){
//        super.loadAll();
//        for (QuestLocation ql : getCache().values()) {
//            if (ql.getType() == QuestLocationType.RADIUS){
//                questRads.put(ql.getLocation(), ql);
//            } else {
//                questBlocks.put(ql.getLocation(), ql);
//            }
//        }
//    }

    public void addQuestLocation(Location<World> location, String questId, double radius, QuestLocationType type) {
        Optional<QuestLocation> questLocation = questService.getQuest(questId).map(quest -> {
            return new QuestLocation(location, quest, radius, type);
        });


        if (questLocation.isPresent()) {

            QuestLocation questLoc = questLocation.get();

            if (questLoc.getType() == QuestLocationType.RADIUS) {

                // if any quest locations already overlap this quest location, return
                if ( repository.cacheParallelStream().anyMatch(ql -> ql.overlaps(questLoc)) ) return;

                questRads.put(questLoc.getLocation(), questLoc);

            } else if (questLoc.getType() == QuestLocationType.BLOCK) {

                // if any quest locations already have the same location as this one, return
                if ( repository.cacheParallelStream().anyMatch(ql -> ql.sameBlockAs(questLoc.getLocation())) ) return;

                questBlocks.put(questLoc.getLocation(), questLoc);
            }

            repository.saveOne(questLoc);
        }
    }

    public void remove(QuestLocation questLocation) {

    }

//    @Override
//    protected Optional<Document> toDocument(QuestLocation questLocation) {
//        Document document = new Document();
//
//        document.append("location", AtherysQuests.getGson().toJson(questLocation.getLocation()));
//        document.append("questId", questLocation.getQuestId());
//        document.append("radius", questLocation.getRadius());
//        document.append("type", questLocation.getType().name());
//
//        return Optional.of(document);
//    }
//
//    @Override
//    protected Optional<QuestLocation> fromDocument(Document document) {
//        Location location = AtherysQuests.getGson().fromJson((String) document.get("location"), Location.class);
//        Optional<Quest> quest = AtherysQuests.getQuestService().getQuest(document.getString("questId"));
//        QuestLocationType type = QuestLocationType.valueOf(document.getString("type"));
//
//        return quest.map(quest1 -> new QuestLocation(location, quest1, document.getDouble("radius"), type));
//    }
}