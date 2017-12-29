package com.atherys.quests.quester;

import com.atherys.quests.quest.Quest;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Event;

import javax.annotation.Nullable;
import java.util.*;

public class Quester {

    private UUID player;

    private Player cachedPlayer;
    private Map<String,Quest> quests = new HashMap<>();
    private List<String> completedQuestIds;

    public void notify ( Event event, Player player ) {
        if ( !this.player.equals( player.getUniqueId() ) ) return;

        this.cachedPlayer = player;
        for ( Quest quest : quests.values() ) {
            quest.notify( event, this );
        }
    }

    public void pickupQuest ( Quest quest ) {
        if ( !completedQuestIds.contains(quest.getId()) && !quests.containsKey( quest.getId() ) ) {
            quests.put( quest.getId(), quest.copy() );
        }
    }

    public void removeQuest ( Quest quest ) {
        quests.remove( quest.getId() );
    }

    public void completeQuest ( Quest quest ) {
        quests.remove( quest.getId() );
        completedQuestIds.add( quest.getId() );
    }

    public Optional<User> getUser() {
        // TODO: Use UserUtils to look up UUID of player and return User object.
        return Optional.empty();
    }

    @Nullable
    public Player getCachedPlayer() {
        return cachedPlayer;
    }
}
