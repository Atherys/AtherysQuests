package com.atherys.quests.service;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.QuestLocationType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.*;

/**
 * This is the middleman between the commands to set quests and right clicking to set them.
 */

public class QuestCommandService {
    private Map<UUID, ProtoQuestLocation> questSetters = new HashMap<>();
    private List<UUID> questRemovers = new ArrayList<>();

    private static QuestCommandService instance = new QuestCommandService();

    public static QuestCommandService getInstance(){
        return instance;
    }

    public void startQuestRemoval(Player player){
        questRemovers.add(player.getUniqueId());
    }

    public boolean isRemovingQuest(Player player){
        return questRemovers.contains(player.getUniqueId());
    }

    public void endQuestRemoval(Player player){
        questRemovers.remove(player.getUniqueId());
    }

    public void startQuestAttachment(Player player, String questId, double radius, QuestLocationType type){
        questSetters.put(player.getUniqueId(), new ProtoQuestLocation(questId, radius, type));
    }

    public void addQuestLocation(Player player, Location<World> location){
        Optional<ProtoQuestLocation> proto = getQuestAttachment(player);
        proto.ifPresent(questLocation ->{
            AtherysQuests.getLocationManager().addQuestLocation(location, questLocation.getQuestId(), questLocation.getRadius(), questLocation.getType());
        });
    }

    private Optional<ProtoQuestLocation> getQuestAttachment(Player player){
       return Optional.ofNullable(questSetters.get(player.getUniqueId()));
    }

    public boolean isAttachingQuest(Player player){
        return questSetters.containsKey(player.getUniqueId());
    }

    public void endQuestAttachment(Player player){
        questSetters.remove(player.getUniqueId());
    }

    private static class ProtoQuestLocation {
        private double radius;
        private String questId;
        private QuestLocationType type;

        private ProtoQuestLocation(String questId, double radius, QuestLocationType type){
            this.questId = questId;
            this.radius = radius;
            this.type = type;
        }

        private String getQuestId() {
            return questId;
        }

        private double getRadius() {
            return radius;
        }

        private QuestLocationType getType() {
            return type;
        }
    }
}
