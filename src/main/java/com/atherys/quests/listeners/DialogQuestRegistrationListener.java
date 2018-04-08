package com.atherys.quests.listeners;

import com.atherys.quests.events.DialogRegistrationEvent;
import com.atherys.quests.events.QuestRegistrationEvent;
import org.spongepowered.api.event.Listener;

public class DialogQuestRegistrationListener {

    private DummyQuest.Staged stagedQuest;

    @Listener
    public void onQuestRegistration ( QuestRegistrationEvent event ) {
        stagedQuest = new DummyQuest.Staged();
        event.register( stagedQuest );
    }

    @Listener
    public void onDialogRegistration ( DialogRegistrationEvent event ) {
        event.register( DummyQuest.dialog( stagedQuest ) );
    }

}
