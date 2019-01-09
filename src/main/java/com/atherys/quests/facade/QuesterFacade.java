package com.atherys.quests.facade;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.service.QuesterService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.player.Player;

@Singleton
public class QuesterFacade {

    @Inject
    private QuesterService service;

    public <T extends Quest> boolean pickupQuest(Player player, Quest<T> quest) {
        return false;
    }

    public <T extends Quest> boolean turnInQuest(Player player, Quest<T> quest) {
        return false;
    }
}
