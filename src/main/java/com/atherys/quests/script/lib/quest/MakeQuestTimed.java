package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quest.modifiers.TimeComponent;

import java.util.function.BiFunction;

/**
 * @jsfunc
 */
public class MakeQuestTimed implements BiFunction<Quest, Integer, Boolean> {
    /**
     * Makes a quest timed.
     * @param quest The quest.
     * @param seconds How many seconds before the quest is failed and removed.
     */
    @Override
    public Boolean apply(Quest quest, Integer seconds) {
        quest.makeTimed(new TimeComponent(seconds, null));
        return true;
    }
}
