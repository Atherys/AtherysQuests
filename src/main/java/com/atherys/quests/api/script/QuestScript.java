package com.atherys.quests.api.script;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.quester.Quester;

public interface QuestScript extends Script {

    void onPickUp(Quest quest, Quester quester);

    void onBegin(Quest quest, Quester quester);

    void onProgress(Quest quest, Quester quester, Objective objective);

    void onComplete(Quest quest, Quester quester);

    void onTurnIn(Quest quest, Quester quester);

}
