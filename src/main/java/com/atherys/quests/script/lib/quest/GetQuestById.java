package com.atherys.quests.script.lib.quest;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.script.api.function.ScriptFunction;

/**
 * @jsfunc
 */
public class GetQuestById implements ScriptFunction<String, Quest> {
    @Override
    public Quest call(String questId) {
        return AtherysQuests.getInstance().getQuestService().getQuest(questId).orElse(null);
    }
}
