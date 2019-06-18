package com.atherys.quests.script.lib.quest;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.script.api.function.ScriptFunction;

/**
 * @jsfunc
 */
public class RegisterQuest implements ScriptFunction<Quest, Boolean> {
    @Override
    public Boolean call(Quest quest) {
        AtherysQuests.getInstance().getQuestService().registerQuest(quest);
        return true;
    }
}
