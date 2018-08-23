package com.atherys.quests.service;

import com.atherys.core.interaction.AbstractAttachmentService;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.QuestLocationType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.*;

/**
 * This is the middleman between the commands to set quests and right clicking to set them.
 */

public class QuestAttachmentService extends AbstractAttachmentService<QuestAttachmentService.ProtoQuestLocation> {

    private QuestAttachmentService() {
        super();
    }

    private static QuestAttachmentService instance = new QuestAttachmentService();

    public static QuestAttachmentService getInstance(){
        return instance;
    }

    public void addQuestLocation(Player player, Location<World> location){
        Optional<ProtoQuestLocation> proto = getAttachment(player);
        proto.ifPresent(questLocation ->{
            AtherysQuests.getLocationManager().addQuestLocation(location, questLocation.getQuestId(), questLocation.getRadius(), questLocation.getType());
        });
    }

    public void startAttachment(Player player, String questId, Double radius, QuestLocationType type) {
        startAttachment(player, new ProtoQuestLocation(questId, radius, type));
    }

    @Override
    public void applyAttachment(Player player) {

    }

    protected static class ProtoQuestLocation {
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
