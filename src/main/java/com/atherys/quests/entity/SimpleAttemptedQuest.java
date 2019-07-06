package com.atherys.quests.entity;

import com.atherys.core.db.Identifiable;
import com.atherys.quests.api.quest.AttemptedQuest;

import javax.annotation.Nonnull;
import javax.persistence.*;

@Entity
public class SimpleAttemptedQuest implements AttemptedQuest, Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "times_completed")
    private int timesCompleted = 0;

    @Column(name = "timestamp")
    private long timestamp;

    public SimpleAttemptedQuest() {
    }

    @Override
    public int timesCompleted() {
        return timesCompleted;
    }

    @Override
    public void incrementTimesCompleted() {
        timesCompleted++;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Nonnull
    @Override
    public Long getId() {
        return id;
    }
}
