package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quest.modifiers.Timeable;
import com.atherys.quests.api.quester.Quester;
import com.atherys.script.api.util.TriFunction;

import java.util.function.Consumer;

/**
 * @jsfunc
 */
public class MakeQuestTimed implements TriFunction<Quest, Integer, Consumer<Quester>, Boolean> {
    /**
     * Makes a quest timed.
     * @param quest The quest.
     * @param seconds How many seconds before the quest is failed and removed.
     * @param onComplete A function that accepts a `Quest` and a `Quester`. This will run when the timer ends. Can be null.
     */
    @Override
    public Boolean apply(Quest quest, Integer seconds, Consumer<Quester> onComplete) {
        quest.makeTimed(new Timeable(seconds, onComplete));
        return true;
    }
}
