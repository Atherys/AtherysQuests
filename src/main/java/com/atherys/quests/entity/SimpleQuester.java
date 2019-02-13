package com.atherys.quests.entity;

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

    @ElementCollection
    @Convert(converter = QuestConverter.class)
    private Set<Quest> ongoingQuests = new HashSet<>();

    @ElementCollection
    @MapKeyColumn(name = "quest_id")
    @Column(name = "timestamp")
    @CollectionTable(name = "simplequester_finishedquests")
    private Map<String, Long> finishedQuests = new HashMap<>();

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
    public Map<String, Long> getFinishedQuests() {
        return finishedQuests;
    }

    @Override
    public void addFinishedQuest(String questId, Long timestamp) {
        finishedQuests.put(questId, timestamp);
    }

    @Override
    public boolean hasFinishedQuest(String questId) {
        return finishedQuests.containsKey(questId);
    }

    @Override
    public void removeFinishedQuest(String questId) {
        finishedQuests.remove(questId);
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
    public void removeQuest(Quest quest) {
        ongoingQuests.remove(quest);
    }

}
