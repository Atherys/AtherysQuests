package com.atherys.quests.listener;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.event.quest.QuestCompletedEvent;
import com.atherys.quests.event.quest.QuestStartedEvent;
import com.google.inject.Singleton;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

@Singleton
public class QuestListener {
    @Listener
    public void onTimedQuestStart(QuestStartedEvent event) {
        if (event.getQuest().getTimedComponent().isPresent()) {
            AtherysQuests.getInstance().getQuesterFacade().onStartTimedQuest(event.getQuester(), event.getQuest());
        }
    }

    @Listener
    public void onTimedQuestComplete(QuestCompletedEvent event) {
        if (event.getQuest().getTimedComponent().isPresent()) {
            AtherysQuests.getInstance().getQuesterFacade().onCompleteTimedQuest(event.getQuester());
        }
    }

    @Listener
    public void onQuesterLogin(ClientConnectionEvent.Join event) {
        AtherysQuests.getInstance().getQuesterFacade().resetTimedQuestOnLogin(event.getTargetEntity());
    }
}
