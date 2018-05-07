package com.atherys.quests.api.quest;

import com.atherys.quests.quester.Quester;

public interface Script {

    String getId();

    void pickUp(Quester quester);

    void progress(Quester quester);

    void complete(Quester quester);

    void turnIn(Quester quester);

}
