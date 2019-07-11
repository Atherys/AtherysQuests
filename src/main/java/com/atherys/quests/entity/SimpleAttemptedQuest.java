package com.atherys.quests.entity;

import com.atherys.core.db.Identifiable;
import com.atherys.quests.api.quest.AttemptedQuest;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "attempted_quest", schema = "atherys")
public class SimpleAttemptedQuest implements AttemptedQuest, Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questId;

    @Column(name = "times_completed")
    private int timesCompleted = 0;

    @Column(name = "timestamp")
    private long timestamp;

    public SimpleAttemptedQuest(String questId) {
        this.questId = questId;
    }

    public SimpleAttemptedQuest() {
    }

    @Override
    public String getQuestId() {
        return questId;
    }

    @Override
    public int getTimesCompleted() {
        return timesCompleted;
    }

    @Override
    public void setTimesCompleted(int times) {
        this.timesCompleted = times;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleAttemptedQuest)) return false;
        SimpleAttemptedQuest that = (SimpleAttemptedQuest) o;
        return questId.equals(that.questId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questId);
    }
}
