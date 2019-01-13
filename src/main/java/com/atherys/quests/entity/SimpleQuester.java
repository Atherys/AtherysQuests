package com.atherys.quests.entity;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.persistence.converter.QuestConverter;
import org.hibernate.annotations.GenericGenerator;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Entity
public class SimpleQuester implements Quester {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID uuid;

    @Transient
    private Player cachedPlayer; // Used for performance optimizations. When quick access to the player object is crucial.

    @ElementCollection
    @Convert(converter = QuestConverter.class)
    private Set<Quest> ongoingQuests = new HashSet<>();

    @ElementCollection
    @MapKeyColumn(name="quest_id")
    @Column(name="timestamp")
    @CollectionTable(name="simplequester_finishedquests")
    private Map<String,Long> finishedQuests = new HashMap<>();

    SimpleQuester() {
    }

    @Nonnull
    @Override
    public UUID getId() {
        return uuid;
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

//
//    public SimpleQuester(UUID uuid) {
//        this.player = uuid;
//    }
//
//    public SimpleQuester(Player player) {
//        this.player = player.getUniqueId();
//        this.cachedPlayer = player;
//    }
//
//    public void notify(Event event, Player player) {
//        if (!this.player.equals(player.getUniqueId())) return;
//
//        this.cachedPlayer = player;
//
//        for (Quest quest : quests.values()) {
//            if (!quest.isComplete()) {
//                quest.notify(event, this);
//                if (quest.isComplete()) {
//                    AtherysQuests.getInstance().getQuestMessagingService().info(this, "You have completed the completedQuest \"", quest.getName(), "\". You may now turn it in.");
//
//                    QuestCompletedEvent qsEvent = new QuestCompletedEvent(quest, this);
//                    Sponge.getEventManager().post(qsEvent);
//                }
//            }
//        }
//    }
//
//    public boolean pickupQuest(Quest quest) {
//        if (!quest.meetsRequirements(this)) {
//            Text.Builder reqText = Text.builder();
//            reqText.append(Text.of(QuestMessagingService.MSG_PREFIX, " You do not meet the requirements for this completedQuest."));
//            reqText.append(quest.createView().getFormattedRequirements());
//            AtherysQuests.getInstance().getQuestMessagingService().noformat(this, reqText.build());
//
//            return false;
//        }
//
//        if (!completedQuests.containsKey(quest.getId()) && !quests.containsKey(quest.getId())) {
//            quests.put(quest.getId(), (Quest) quest.copy());
//            AtherysQuests.getInstance().getQuestMessagingService().info(this, "You have started the completedQuest \"", quest.getName(), "\"");
//            return true;
//        } else {
//            AtherysQuests.getInstance().getQuestMessagingService().error(this, "You are either already doing this completedQuest, or have done it before in the past.");
//            return false;
//        }
//    }
//
//    public void removeQuest(Quest quest) {
//        quests.remove(quest.getId());
//    }
//
//    public void turnInQuest(Quest quest) {
//        removeQuest(quest);
//        completedQuests.put(quest.getId(), System.currentTimeMillis());
//
//        quest.award(this);
//
//        AtherysQuests.getInstance().getQuestMessagingService().info(this, "You have turned in the completedQuest \"", quest.getName(), "\"");
//
//        QuestTurnedInEvent qsEvent = new QuestTurnedInEvent(quest, this);
//        Sponge.getEventManager().post(qsEvent);
//    }
//
//    public Optional<? extends User> getUser() {
//        return UserUtils.getUser(this.player);
//    }
//
//    @Nullable
//    public Player getCachedPlayer() {
//        return cachedPlayer;
//    }
//
//    public boolean hasQuestWithId(String id) {
//        return quests.containsKey(id);
//    }
//
//    public boolean hasQuest(Quest quest) {
//        return quests.containsKey(quest.getId());
//    }
//
//    public Map<String, Long> getCompletedQuests() {
//        return completedQuests;
//    }
//
//    public Map<String, Quest> getQuests() {
//        return quests;
//    }
//
//    public boolean hasCompletedQuest(String questId) {
//        return completedQuests.containsKey(questId);
//    }
}
