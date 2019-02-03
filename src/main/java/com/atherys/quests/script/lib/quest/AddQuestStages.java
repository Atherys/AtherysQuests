package com.atherys.quests.script.lib.quest;

import com.atherys.quests.quest.Stage;
import com.atherys.quests.quest.StagedQuest;

import java.util.function.BiFunction;

/**
 * @jsfunc
 */
public class AddQuestStages implements BiFunction<StagedQuest, Stage[], Boolean> {
    @Override
    public Boolean apply(StagedQuest stagedQuest, Stage[] stages) {
        for (Stage stage : stages) {
            stagedQuest.getStages().add(stage);
        }

        return true;
    }
}
