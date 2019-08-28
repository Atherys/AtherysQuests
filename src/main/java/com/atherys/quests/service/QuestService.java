package com.atherys.quests.service;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.data.QuestData;
import com.google.inject.Singleton;
import org.spongepowered.api.data.DataHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public final class QuestService {

    private Map<String, Quest> quests = new HashMap<>();

    QuestService() {
    }

    public void registerQuest(Quest quest) {
        this.quests.put(quest.getId(), quest);
    }

    public void unregisterQuest(Quest quest) {
        quests.remove(quest.getId());
    }

    public void unregisterQuests() {
        quests.clear();
    }

    public Optional<Quest> getQuest(String questId) {
        return Optional.ofNullable(quests.get(questId));
    }

    public Optional<Quest> getQuest(DataHolder holder) {
        Optional<QuestData> questData = holder.get(QuestData.class);
        if (questData.isPresent()) return this.getQuest(questData.get().getQuestId());
        else return Optional.empty();
    }

    public Collection<Quest> getAllQuests() {
        return quests.values();
    }

    public boolean setQuest(DataHolder holder, Quest quest) {
        return holder.offer(new QuestData(quest.getId())).isSuccessful();
    }

}
