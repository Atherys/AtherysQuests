package com.atherys.quests.api.quest;

public interface AttemptedQuest {
    String getQuestId();

    int getTimesCompleted();

    void setTimesCompleted(int times);

    long getFirstTimestamp();

    void setFirstTimestamp(long timestamp);

    long getTimestamp();

    void setTimestamp(long timestamp);
}
