package com.atherys.quests.event;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.service.QuestService;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

/**
 * An event for registering quests with the {@link QuestService}. This event is called immediately before {@link DialogRegistrationEvent}.
 */
public class QuestRegistrationEvent implements Event {

    private Cause cause;

    public QuestRegistrationEvent(QuestService manager) {
        this.cause = Cause.builder().append(AtherysQuests.getInstance()).append(manager).build(Sponge.getCauseStackManager().getCurrentContext());
    }

    @Override
    public Cause getCause() {
        return cause;
    }

    public QuestService getManager() {
        return QuestService.getInstance();
    }

    public void register(Quest quest) {
        getManager().registerQuest(quest);
    }
}
