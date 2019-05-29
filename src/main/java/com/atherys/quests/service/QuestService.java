package com.atherys.quests.service;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.data.QuestData;
import com.atherys.quests.event.quest.QuestRegistrationEvent;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataHolder;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public final class QuestService {

    private Map<String, Quest> quests = new HashMap<>();

    QuestService() {
    }

    @PostConstruct
    private void init() {
        QuestRegistrationEvent questRegistrationEvent = new QuestRegistrationEvent(this);
        Sponge.getEventManager().post(questRegistrationEvent);
    }

    public void registerQuest(Quest quest) {
        this.quests.put(quest.getId(), quest);
    }

    public void unregisterQuest(Quest quest) {
        quests.remove(quest.getId());
    }

    public Optional<Quest> getQuest(String questId) {
        return Optional.ofNullable(quests.get(questId));
    }

    public Optional<Quest> getQuest(DataHolder holder) {
        Optional<QuestData> questData = holder.get(QuestData.class);
        if (questData.isPresent()) return this.getQuest(questData.get().getQuestId());
        else return Optional.empty();
    }

    public boolean setQuest(DataHolder holder, Quest quest) {
        return holder.offer(new QuestData(quest.getId())).isSuccessful();
    }

    public boolean hasQuesterTurnedInQuest(Quester quester, String questId) {
        return quester.hasFinishedQuest(questId);
    }

    public boolean hasQuesterCompletedQuest(Quester quester, String questId) {
        Optional<Quest> completedQuest =  quester.getOngoingQuests().stream()
                .filter(quest -> quest.getId().equals(questId))
                .filter(Quest::isComplete)
                .findFirst();

        return completedQuest.isPresent();
    }

    public Set<Quest> getDeliverableQuests() {
        return quests.values().stream()
                .filter(quest -> quest.getDeliverableComponent().isPresent())
                .collect(Collectors.toSet());
    }
}
