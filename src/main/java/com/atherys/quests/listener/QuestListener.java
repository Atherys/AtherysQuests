package com.atherys.quests.listener;

import com.atherys.quests.event.quest.QuestCompletedEvent;
import com.atherys.quests.event.quest.QuestStartedEvent;
import com.atherys.quests.facade.QuesterFacade;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

@Singleton
public class QuestListener {
    @Inject
    QuesterFacade questerFacade;

    @Listener
    public void onTimedQuestStart(QuestStartedEvent event) {
        if (event.getQuest().getTimedComponent().isPresent()) {
            questerFacade.onStartTimedQuest(event.getQuest());
        }
    }

    @Listener
    public void onTimedQuestComplete(QuestCompletedEvent event) {
        if (event.getQuest().getTimedComponent().isPresent()) {
            questerFacade.onCompleteTimedQuest(event.getQuester());
        }
    }

    @Listener
    public void onQuesterLogin(ClientConnectionEvent.Join event) {
        questerFacade.resetTimedQuestOnLogin(event.getTargetEntity());
    }
}
