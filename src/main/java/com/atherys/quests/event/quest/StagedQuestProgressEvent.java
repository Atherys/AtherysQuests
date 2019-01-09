package com.atherys.quests.event.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.model.SimpleQuester;
import com.atherys.quests.model.quest.Stage;

public class StagedQuestProgressEvent extends AbstractQuestEvent {

    private Stage stage;

    public StagedQuestProgressEvent(Quest quest, Stage stage, Quester simpleQuester) {
        super(quest, simpleQuester);
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
