package com.atherys.quests.api.quester;

import com.atherys.core.db.SpongeIdentifiable;
import com.atherys.core.views.Viewable;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.views.QuestLog;

import java.util.Set;

public interface Quester extends SpongeIdentifiable, Viewable<QuestLog> {

    Set<Quest> getFinishedQuests();

    void addFinishedQuest(Quest quest);

    boolean hasFinishedQuest(Quest quest);

    void removeFinishedQuest(Quest quest);

    Set<Quest> getOngoingQuests();

    void addQuest(Quest quest);

    boolean hasQuest(Quest quest);

    void removeQuest(Quest quest);

    default QuestLog getLog() {
        return createView();
    }

}
