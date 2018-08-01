package com.atherys.quests.service;

import org.spongepowered.api.entity.living.player.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * This is the middleman between the commands to set quests and right clicking to set them.
 */

public class QuestAdminService {
    private HashMap<UUID, QuestSet> questSetters;

    private static QuestAdminService instance = new QuestAdminService();

    public static QuestAdminService getInstance(){
        return instance;
    }

    public void startQuestSet(Player player, String questId, double radius){
        questSetters.put(player.getUniqueId(), new QuestSet(questId, radius));
    }

    public QuestSet getQuestSet(Player player){
       return questSetters.get(player.getUniqueId());
    }

    public boolean isSettingQuest(Player player){
        return questSetters.containsKey(player.getUniqueId());
    }

    public void endQuestSet(Player player){
        questSetters.remove(player.getUniqueId());
    }

    public static class QuestSet {
        private double radius;
        private String questId;

        private QuestSet(String questId, double radius){
            this.questId = questId;
            this.radius = radius;
        }

        public String getQuestId() {
            return questId;
        }

        public double getRadius() {
            return radius;
        }
    }
}
