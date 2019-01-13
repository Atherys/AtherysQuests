package com.atherys.quests.service;

import com.atherys.quests.api.quest.QuestLocationType;
import com.atherys.quests.entity.QuestLocation;
import com.atherys.quests.persistence.QuestLocationRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public final class QuestLocationService {

    @Inject
    QuestLocationRepository repository;

    @Inject
    QuestService questService;

    private Map<Location, QuestLocation> questBlocks = new HashMap<>();

    private Map<Location, QuestLocation> questRads = new HashMap<>();

    QuestLocationService() {
    }

    public Map<Location, QuestLocation> getQuestBlocks() {
        return questBlocks;
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

    /**
     * Check if the Quest Location contains the provided Location
     *
     * @param ql The Quest Location
     * @param loc The Location
     * @return Whether the location is contained within the radius of the quest location
     */
    public boolean checkContain(QuestLocation ql, Location<World> loc) {
        return loc.getExtent().equals(ql.getLocation().getExtent())
                && loc.getPosition().distanceSquared(ql.getLocation().getPosition()) <= Math.pow(ql.getRadius(), 2);
    }

    /**
     * Retrieve a Quest Location by radius ( if the provided location is within it's radius, it will be returned )
     *
     * @param location the location to search for
     * @return A quest location. Empty if not found.
     */
    public Optional<QuestLocation> getByRadius(Location<World> location) {
        for (QuestLocation ql : questRads.values()) {
            if (checkContain(ql, location)) {
                return Optional.of(ql);
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieve a Quest Location by radius ( if the provided location is within it's radius, it will be returned )
     *
     * @param location the location to search for
     * @return A quest location. Empty if not found.
     */
    public Optional<QuestLocation> getByBlock(Location<World> location) {
        for (QuestLocation ql : questBlocks.values()) {
            if (checkSameBlock(ql.getLocation(), location)) {
                return Optional.of(ql);
            }
        }
        return Optional.empty();
    }

    /**
     * Gets the quest block if there is a quest block at the location, and if not tries to get a radius one.
     *
     * @param location The location
     * @return the quest location. empty if not found.
     */
    public Optional<QuestLocation> getByLocation(Location<World> location) {
        Optional<QuestLocation> questLocation = getByBlock(location);
        if (questLocation.isPresent()) {
            return questLocation;
        } else {
            return getByRadius(location);
        }
    }

    public void addQuestLocation(Location<World> location, String questId, double radius, QuestLocationType type) {
        Optional<QuestLocation> questLocation = questService.getQuest(questId).map(quest ->
            new QuestLocation(location, quest, radius, type)
        );


        if (questLocation.isPresent()) {

            QuestLocation questLoc = questLocation.get();

            if (questLoc.getType() == QuestLocationType.RADIUS) {

                // if any quest locations already overlap this quest location, return
                if (repository.cacheParallelStream().anyMatch(ql -> checkOverlap(ql, questLoc))) return;

                questRads.put(questLoc.getLocation(), questLoc);

            } else if (questLoc.getType() == QuestLocationType.BLOCK) {

                // if any quest locations already have the same location as this one, return
                if (repository.cacheParallelStream().anyMatch(ql -> checkSameBlock(ql.getLocation(), questLoc.getLocation()))) return;

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