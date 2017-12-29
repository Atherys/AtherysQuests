package com.atherys.quests.managers;

import com.atherys.quests.QuestKeys;
import com.atherys.quests.quest.Quest;
import org.spongepowered.api.data.DataHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class QuestManager {

    private static QuestManager instance;

    private Map<String,Quest> quests = new HashMap<>();

    private QuestManager() {}

    public void registerQuest ( Quest quest ) {
        this.quests.put( quest.getId(), quest );
    }

    public Optional<Quest> getQuest ( String questId ) {
        return Optional.ofNullable( quests.get( questId ) );
    }

    public Optional<Quest> getQuest ( DataHolder holder ) {
        Optional<String> questId = holder.get(QuestKeys.QUEST);
        if ( questId.isPresent() ) {
            return Optional.ofNullable( quests.get( questId.get() ) );
        } else return Optional.empty();
    }

    public static QuestManager getInstance() {
        return instance;
    }
}
