package com.atherys.quests.quester;

import com.atherys.quests.quest.Quest;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Quester {

    private UUID player;

    private Map<String,Quest> quests = new HashMap<>();

    public void notify ( Event event ) {
        for ( Quest quest : quests.values() ) {
            quest.notify( event );
        }
    }

    public void addQuest ( Quest quest ) {
        if ( !quests.containsKey( quest.getId() ) ) quests.put( quest.getId(), quest.copy() );
    }

    public void removeQuest ( Quest quest ) {
        quests.remove( quest.getId() );
    }

    public Optional<User> getUser() {
        // TODO: USe UserUtils to look up UUID of player and return User object.
        return Optional.empty();
    }
}
