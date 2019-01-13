package com.atherys.quests.entity;

import com.atherys.core.db.Identifiable;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quest.QuestLocationType;
import com.atherys.quests.persistence.converter.LocationConverter;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.annotation.Nonnull;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class QuestLocation implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = LocationConverter.class)
    private Location<World> location;

    private double radius;

    private QuestLocationType type;

    private String questId;

    public QuestLocation(Location<World> location, Quest quest, double radius, QuestLocationType type) {
        this.location = location;
        this.radius = radius;
        this.questId = quest.getId();
        this.type = type;
    }

    public QuestLocation() {
    }

    @Nonnull
    @Override
    public Long getId() {
        return id;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Location<World> getLocation() {
        return location;
    }

    public void setLocation(Location<World> location) {
        this.location = location;
    }

    public String getQuestId() {
        return questId;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }

    public QuestLocationType getType() {
        return type;
    }

    public void setType(QuestLocationType type) {
        this.type = type;
    }

//    public boolean overlaps(QuestLocation questLocation) {
//        return (this.location.getPosition().distanceSquared(questLocation.location.getPosition())
//                < Math.pow(this.radius + questLocation.radius, 2));
//    }
//
//    public boolean sameBlockAs(Location<World> location) {
//        return (this.location.getExtent().equals(location.getExtent()) &&
//                this.location.getBlockPosition().equals(location.getBlockPosition()));
//    }
//
//    public boolean contains(Location<World> loc) {
//        if (loc.getExtent().equals(location.getExtent())) {
//            return (loc.getPosition().distanceSquared(location.getPosition()) <= Math.pow(radius, 2));
//        } else return false;
//    }

}