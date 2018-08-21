package com.atherys.quests.event.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.quest.Stage;
import com.atherys.quests.quester.Quester;

public class StagedQuestProgressEvent extends AbstractQuestEvent {

    private Stage stage;

    public StagedQuestProgressEvent(Quest quest, Stage stage, Quester quester) {
        super(quest, quester);
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
