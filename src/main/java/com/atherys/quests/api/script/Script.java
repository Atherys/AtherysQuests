package com.atherys.quests.api.script;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.quester.Quester;

public interface Script {

    String getId();

    void onLoad(Quest quest);

    void onPickUp(Quest quest, Quester quester);

    void onProgress(Quest quest, Quester quester);

    void onComplete(Quest quest, Quester quester);

    void onTurnIn(Quest quest, Quester quester);

}
