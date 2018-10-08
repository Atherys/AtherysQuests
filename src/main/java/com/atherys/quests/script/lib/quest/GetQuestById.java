package com.atherys.quests.script.lib.quest;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;

import java.util.function.Function;

/**
 * @jsfunc
 */ 
public class GetQuestById implements Function<String,Quest> {
    @Override
    public Quest apply(String questId) {
        return AtherysQuests.getQuestService().getQuest(questId).orElse(null);
    }
}
