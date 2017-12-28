package com.atherys.quests.quest;

import com.atherys.quests.QuestKeys;
import org.spongepowered.api.data.DataHolder;

import java.util.Map;
import java.util.Optional;

public class QuestManager {

    private static QuestManager instance;

    private Map<String,Quest> quests;

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
