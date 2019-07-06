package com.atherys.quests.api.quest;

public interface AttemptedQuest {
    int timesCompleted();

    void incrementTimesCompleted();

    long getTimestamp();

    void setTimestamp(long timestamp);
}
