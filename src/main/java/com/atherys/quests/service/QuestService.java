package com.atherys.quests.service;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.data.QuestData;
import com.atherys.quests.event.quest.QuestRegistrationEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class QuestService {

    private static QuestService instance = new QuestService();

    private Map<String, Quest> quests = new HashMap<>();

    private QuestService() {
        QuestRegistrationEvent questRegistrationEvent = new QuestRegistrationEvent(this);
        Sponge.getEventManager().post(questRegistrationEvent);
    }

    public static QuestService getInstance() {
        return instance;
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
}
