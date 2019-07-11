package com.atherys.quests.entity;

import com.atherys.quests.api.quest.AttemptedQuest;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.persistence.converter.QuestConverter;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.*;

@Entity
public class SimpleQuester implements Quester {

    @Id
    private UUID id;

    @Transient
    private Player cachedPlayer; // Used for performance optimizations. When quick access to the player object is crucial.

    @Transient
    private Quest timedQuest;

    @ElementCollection(fetch = FetchType.EAGER)
    @Convert(converter = QuestConverter.class)
    @Column(columnDefinition = "text")
    private Set<Quest> ongoingQuests = new HashSet<>();

    @OneToMany(
            targetEntity = SimpleAttemptedQuest.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @MapKeyColumn(name = "questId")
    private Map<String, AttemptedQuest> attemptedQuests = new HashMap<>();

    public SimpleQuester() {
    }

    @Nonnull
    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID uuid) {
        this.id = uuid;
    }

    public Player getCachedPlayer() {
        return cachedPlayer;
    }

    public void setCachedPlayer(Player cachedPlayer) {
        this.cachedPlayer = cachedPlayer;
    }

    @Override
    public void addAttemptedQuest(String questId, AttemptedQuest quest) {
        attemptedQuests.put(questId, quest);
    }

    @Override
    public boolean removeAttemptedQuest(String questId) {
        return attemptedQuests.remove(questId) != null;
    }

    @Override
    public Optional<AttemptedQuest> getAttemptedQuest(String questId) {
        return Optional.ofNullable(attemptedQuests.get(questId));
    }

    @Override
    public Set<Quest> getOngoingQuests() {
        return ongoingQuests;
    }

    @Override
    public void addQuest(Quest quest) {
        ongoingQuests.add(quest);
    }

    @Override
    public boolean hasQuest(Quest quest) {
        return ongoingQuests.contains(quest);
    }

    @Override
    public boolean removeQuest(Quest quest) {
        return ongoingQuests.remove(quest);
    }

    @Override
    public Optional<Quest> getTimedQuest() {
        return Optional.ofNullable(timedQuest);
    }

    @Override
    public void setTimedQuest(Quest quest) {
        timedQuest = quest;
    }
}
