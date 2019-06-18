package com.atherys.quests.script.lib.quest;

import com.atherys.quests.quest.Stage;
import com.atherys.quests.quest.StagedQuest;
import com.atherys.script.api.function.ScriptBiFunction;

import java.util.List;

/**
 * @jsfunc
 */
public class AddQuestStages implements ScriptBiFunction<StagedQuest, List<Stage>, Boolean> {
    @Override
    public Boolean call(StagedQuest stagedQuest, List<Stage> stages) {
        for (Stage stage : stages) {
            stagedQuest.getStages().add(stage);
        }

        return true;
    }
}
