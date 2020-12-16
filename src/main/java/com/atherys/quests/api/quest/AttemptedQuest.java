package com.atherys.quests.api.quest;

import com.atherys.quests.api.quester.Quester;

public interface AttemptedQuest {
    String getQuestId();

    int getTimesCompleted();

    void setTimesCompleted(int times);

    long getFirstTimestamp();

    void setFirstTimestamp(long timestamp);

    long getTimestamp();

    void setTimestamp(long timestamp);

    Quester getAttemptedBy();

    void setAttemptedBy(Quester quester);
}
