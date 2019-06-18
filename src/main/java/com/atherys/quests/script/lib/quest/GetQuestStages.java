package com.atherys.quests.script.lib.quest;

import com.atherys.quests.quest.Stage;
import com.atherys.quests.quest.StagedQuest;
import com.atherys.script.api.function.ScriptFunction;

/**
 * @jsfunc
 */
public class GetQuestStages implements ScriptFunction<StagedQuest, Stage[]> {
    @Override
    public Stage[] call(StagedQuest quest) {
        return (Stage[]) quest.getStages().toArray();
    }
}
