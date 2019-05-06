package com.atherys.quests.script.lib.quest;

import com.atherys.quests.quest.Stage;
import com.atherys.quests.quest.StagedQuest;

import java.util.List;
import java.util.function.BiFunction;

/**
 * @jsfunc
 */
public class AddQuestStages implements BiFunction<StagedQuest, List<Stage>, Boolean> {
    @Override
    public Boolean apply(StagedQuest stagedQuest, List<Stage> stages) {
        for (Stage stage : stages) {
            stagedQuest.getStages().add(stage);
        }

        return true;
    }
}
