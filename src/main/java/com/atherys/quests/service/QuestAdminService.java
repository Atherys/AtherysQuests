package com.atherys.quests.service;

import org.spongepowered.api.entity.living.player.Player;

import java.util.HashMap;
import java.util.UUID;

public class QuestAdminService {
    private HashMap<UUID, String> questSetters;

    private static QuestAdminService instance = new QuestAdminService();

    public static QuestAdminService getInstance(){
        return instance;
    }

    public void startQuestSet(Player player, String questId){
        questSetters.put(player.getUniqueId(), questId);
    }

    public String getQuestSet(Player player){
       return questSetters.get(player.getUniqueId());
    }

    public boolean isSettingQuest(Player player){
        return questSetters.containsKey(player.getUniqueId());
    }

    public void endQuestSet(Player player){
        questSetters.remove(player.getUniqueId());
    }
}
