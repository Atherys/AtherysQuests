package com.atherys.quests.api.quester;

import com.atherys.core.db.SpongeIdentifiable;
import com.atherys.quests.api.quest.Quest;

import java.util.Map;
import java.util.Set;

public interface Quester extends SpongeIdentifiable {

    Map<String, Long> getFinishedQuests();

    void addQuest(Quest quest);

    boolean hasQuest(Quest quest);

    void removeQuest(Quest quest);

    Set<Quest> getOngoingQuests();

    void addFinishedQuest(String questId, Long timestamp);

    boolean hasFinishedQuest(String questId);

    void removeFinishedQuest(String questId);

}
