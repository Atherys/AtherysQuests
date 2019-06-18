package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quest.modifiers.TimeComponent;
import com.atherys.script.api.function.ScriptBiFunction;

/**
 * @jsfunc
 */
public class MakeQuestTimed implements ScriptBiFunction<Quest, Integer, Boolean> {
    /**
     * Makes a quest timed.
     * @param quest The quest.
     * @param seconds How many seconds before the quest is failed and removed.
     */
    @Override
    public Boolean call(Quest quest, Integer seconds) {
        quest.makeTimed(new TimeComponent(seconds, null));
        return true;
    }
}
