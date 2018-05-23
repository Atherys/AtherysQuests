package com.atherys.quests.events;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.managers.QuestManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

/**
 * An event for registering quests with the {@link QuestManager}. This event is called immediately before {@link DialogRegistrationEvent}.
 */
public class QuestRegistrationEvent implements Event {

    private Cause cause;

    public QuestRegistrationEvent() {
        this.cause = Cause.builder().append(AtherysQuests.getInstance()).build(Sponge.getCauseStackManager().getCurrentContext());
    }

    @Override
    public Cause getCause() {
        return cause;
    }

    public QuestManager getManager() {
        return QuestManager.getInstance();
    }

    public void register(Quest quest) {
        getManager().registerQuest(quest);
    }
}
