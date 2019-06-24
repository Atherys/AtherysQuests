package com.atherys.quests.api.quester;

import com.atherys.core.db.SpongeIdentifiable;
import com.atherys.quests.api.quest.Quest;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface Quester extends SpongeIdentifiable {

    Map<String, Long> getFinishedQuests();

    void addQuest(Quest quest);

    boolean hasQuest(Quest quest);

    boolean removeQuest(Quest quest);

    Set<Quest> getOngoingQuests();

    Optional<Quest> getTimedQuest();

    void setTimedQuest(Quest quest);

    default void removeTimedQuest() {
        setTimedQuest(null);
    }

    void addFinishedQuest(String questId, Long timestamp);

    boolean hasTurnedInQuest(String questId);

    boolean removeFinishedQuest(String questId);

    default boolean hasCompletedQuest(Quest quest) {
        if (hasQuest(quest)) {
            return getOngoingQuests().stream()
                    .anyMatch(q -> q.equals(quest) && q.isComplete());
        }
        return false;
    }
}
