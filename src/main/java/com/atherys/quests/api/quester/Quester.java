package com.atherys.quests.api.quester;

import com.atherys.core.db.SpongeIdentifiable;
import com.atherys.quests.api.quest.AttemptedQuest;
import com.atherys.quests.api.quest.Quest;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;
import java.util.Set;

/**
 * Represents something (usually a {@link Player}) that can have quests.
 */
public interface Quester extends SpongeIdentifiable {

    Optional<AttemptedQuest> getAttemptedQuest(String questId);

    void addQuest(Quest quest);

    boolean hasQuest(Quest quest);

    /**
     * Removes an ongoing quest.
     * @return Whether a quest was removed or not.
     */
    boolean removeQuest(Quest quest);

    Set<Quest> getOngoingQuests();

    Optional<Quest> getTimedQuest();

    void setTimedQuest(Quest quest);

    default void removeTimedQuest() {
        setTimedQuest(null);
    }

    void addAttemptedQuest(String questId, AttemptedQuest quest);

    boolean removeAttemptedQuest(String questId);

}
