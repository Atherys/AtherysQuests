package com.atherys.quests.quester;

import com.atherys.core.utils.UserUtils;
import com.atherys.quests.events.QuestCompletedEvent;
import com.atherys.quests.events.QuestStartedEvent;
import com.atherys.quests.quest.Quest;
import com.atherys.quests.quest.QuestMsg;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;
import java.util.*;

public class Quester {

    private UUID player; // Retrieve player from this. 100% Reliable.

    private Player cachedPlayer; // Used for performance optimizations. When quick access to the player object is crucial.
    private Map<String,Quest> quests = new HashMap<>();
    private Map<String,Long> completedQuests = new HashMap<>();

    public Quester( Player player ) {
        this.player = player.getUniqueId();
        this.cachedPlayer = player;
    }

    public void notify ( Event event, Player player ) {
        if ( !this.player.equals( player.getUniqueId() ) ) return;

        this.cachedPlayer = player;
        for ( Quest quest : quests.values() ) {
            quest.notify( event, this );
        }
    }

    public void pickupQuest ( Quest quest ) {
        if ( !quest.meetsRequiements( this ) ) {
            Text.Builder reqText = Text.builder();
            reqText.append( Text.of ( QuestMsg.MSG_PREFIX, " You do not meet the requirements for this quest." ) );
            reqText.append( quest.createView().get().getFormattedRequirements() );
            QuestMsg.noformat ( this, reqText.build() );
        }

        if ( !completedQuests.containsKey(quest.getId()) && !quests.containsKey( quest.getId() ) ) {
            quests.put( quest.getId(), quest.copy() );

            QuestStartedEvent qsEvent = new QuestStartedEvent( quest, this );
            Sponge.getEventManager().post( qsEvent );
        } else {
            QuestMsg.error ( this, "You are either already doing this quest, or have done it before in the past." );
        }
    }

    public void removeQuest ( Quest quest ) {
        quests.remove( quest.getId() );
    }

    public void completeQuest ( Quest quest ) {
        quests.remove( quest.getId() );
        completedQuests.put( quest.getId(), System.currentTimeMillis() );

        QuestCompletedEvent qsEvent = new QuestCompletedEvent( quest, this );
        Sponge.getEventManager().post( qsEvent );
    }

    public Optional<? extends User> getUser() {
        return UserUtils.getUser( this.player );
    }

    @Nullable
    public Player getCachedPlayer() {
        return cachedPlayer;
    }

    public boolean hasCompleted(String questId) {
        return completedQuests.containsKey( questId );
    }
}
