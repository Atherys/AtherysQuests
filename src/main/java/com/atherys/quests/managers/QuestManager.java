package com.atherys.quests.managers;

import com.atherys.quests.QuestKeys;
import com.atherys.quests.quest.Quest;
import org.spongepowered.api.data.DataHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class QuestManager {

    private static QuestManager instance = new QuestManager();

    private Map<String, Quest> quests = new HashMap<>();

    private QuestManager() {
    }

    public void registerQuest( Quest quest ) {
        this.quests.put( quest.getId(), quest );
    }

    public void unregisterQuest( Quest quest ) {
        quests.remove( quest.getId() );
    }

    public Optional<Quest> getQuest( String questId ) {
        return Optional.ofNullable( quests.get( questId ) );
    }

    public Optional<Quest> getQuest( DataHolder holder ) {
        return holder.get( QuestKeys.QUEST ).flatMap( this::getQuest );
    }

    public static QuestManager getInstance() {
        return instance;
    }
}
