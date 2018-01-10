package com.atherys.quests.managers;

import com.atherys.quests.quester.Quester;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class QuesterManager {

    private static QuesterManager instance = new QuesterManager();

    private Map<UUID,Quester> questers = new HashMap<>();

    public Quester createQuester ( Player player ) {
        Quester quester = new Quester( player );
        questers.put( player.getUniqueId(), quester );
        return quester;
    }

    public Optional<Quester> getQuester ( Player player ) {
        return Optional.ofNullable( questers.get( player.getUniqueId() ) );
    }

    public void notify ( Event event, Player player ) {
        getQuester( player ).ifPresent( quester -> quester.notify( event, player ) );
    }

    public static QuesterManager getInstance() {
        return instance;
    }
}
