package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * @jsfunc
 */
public class SetOnTimedQuestFail implements BiFunction<Quest<?>, Consumer<Quester>, Boolean> {
    /**
     * Sets a function that will run if the quest's timer runs out.
     * @param quest The quest.
     * @param onFail A function that will run if the quest's time runs out.
     * @return True if setting it worked, false if the quest isn't timed.
     */
    @Override
    public Boolean apply(Quest<?> quest, Consumer<Quester> onFail) {
        if (quest.getTimedComponent().isPresent()) {
            quest.getTimedComponent().get().setOnComplete(onFail);
            return true;
        } else {
            return false;
        }
    }
}
