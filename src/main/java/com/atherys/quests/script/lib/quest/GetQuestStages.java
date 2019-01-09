package com.atherys.quests.script.lib.quest;

import com.atherys.quests.model.quest.Stage;
import com.atherys.quests.model.quest.StagedQuest;

import java.util.function.Function;

/**
 * @jsfunc
 */ 
public class GetQuestStages implements Function<StagedQuest, Stage[]> {
    @Override
    public Stage[] apply(StagedQuest quest) {
        return (Stage[]) quest.getStages().toArray();
    }
}
