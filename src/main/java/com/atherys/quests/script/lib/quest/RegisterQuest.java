package com.atherys.quests.script.lib.quest;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;

import java.util.function.Function;

public class RegisterQuest implements Function<Quest, Boolean> {
    @Override
    public Boolean apply(Quest quest) {
        AtherysQuests.getQuestService().registerQuest(quest);
        return true;
    }
}
