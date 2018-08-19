package com.atherys.quests.event.quest;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.event.dialog.DialogRegistrationEvent;
import com.atherys.quests.service.QuestService;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

/**
 * An event for registering quests with the {@link QuestService}. This event is called immediately before {@link DialogRegistrationEvent}.
 */
public class QuestRegistrationEvent implements Event {

    private Cause cause;

    private QuestService service;

    public QuestRegistrationEvent(QuestService service) {
        this.service = service;
        this.cause = Cause.builder().append(AtherysQuests.getInstance()).append(service).build(Sponge.getCauseStackManager().getCurrentContext());
    }

    @Override
    public Cause getCause() {
        return cause;
    }

    public QuestService getManager() {
        return service;
    }

    public void register(Quest quest) {
        getManager().registerQuest(quest);
    }
}
