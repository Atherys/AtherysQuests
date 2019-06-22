package com.atherys.quests.service;

import com.atherys.core.utils.UserUtils;
import com.atherys.quests.api.base.Observer;
import com.atherys.quests.api.exception.QuestRequirementsException;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.entity.SimpleQuester;
import com.atherys.quests.event.quest.QuestCompletedEvent;
import com.atherys.quests.event.quest.QuestTurnedInEvent;
import com.atherys.quests.persistence.QuesterRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Event;

import java.util.Optional;
import java.util.UUID;

@Singleton
public class QuesterService implements Observer<Event> {

    @Inject
    QuesterRepository repository;

    @Inject
    QuestMessagingService questMsg;

    QuesterService() {
    }

    public SimpleQuester getOrCreateQuester(UUID uuid) {
        Optional<SimpleQuester> questerById = repository.findById(uuid);

        if (questerById.isPresent()) {
            return questerById.get();
        } else {
            SimpleQuester quester = new SimpleQuester();
            quester.setId(uuid);

            repository.saveOne(quester);

            return quester;
        }
    }

    public SimpleQuester getQuester(User user) {
        return getOrCreateQuester(user.getUniqueId());
    }

    public Player getCachedPlayer(Quester quester) {
        return ((SimpleQuester) quester).getCachedPlayer();
    }

    public Player getPlayer(Quester quester) {
        if (quester instanceof SimpleQuester) {
            return ((SimpleQuester) quester).getCachedPlayer();
        }

        return Sponge.getServer().getPlayer(quester.getUniqueId()).orElse(null);
    }

    @Override
    public void notify(Event event, Quester quester) {

        if (quester == null) {
            throw new IllegalStateException("Quester cannot be null. Possibly failed repository lookup.");
        }

        for (Quest quest : quester.getOngoingQuests()) {
            if (!quest.isComplete()) {

                quest.notify(event, quester);

                if (quest.isComplete()) {
                    questMsg.info(quester, "You have completed the quest \"", quest.getName(), "\". You may now turn it in.");

                    QuestCompletedEvent qsEvent = new QuestCompletedEvent(quest, quester);
                    Sponge.getEventManager().post(qsEvent);
                }
            }
        }
    }

    public <T extends Quest> boolean pickupQuest(Quester quester, Quest<T> quest) throws QuestRequirementsException {

        if (!quest.meetsRequirements(quester)) {
            throw new QuestRequirementsException("Quester does not meet quest requirements");
        }

        if (   questerHasTurnedInQuest(quester, quest.getId())
            || questerHasQuest(quester, quest.getId())) {
            return false;
        } else {
            quester.addQuest(quest);
            if (quest.getTimedComponent().isPresent()) {
                quester.setTimedQuest(quest);
            }
            return true;
        }
    }

    public <T extends Quest> boolean turnInQuest(Quester quester, Quest<T> quest) {
        if (questerHasCompletedQuest(quester, quest.getId())) {

            removeQuest(quester, quest);

            quester.addFinishedQuest(quest.getId(), System.currentTimeMillis());

            quest.award(quester);

            QuestTurnedInEvent qsEvent = new QuestTurnedInEvent(quest, quester);
            Sponge.getEventManager().post(qsEvent);

            return true;
        } else {
            return false;
        }
    }

    public Optional<? extends User> getUser(Quester quester) {
        return UserUtils.getUser(quester.getUniqueId());
    }

    public boolean removeQuest(Quester quester, Quest quest) {
        return quester.getOngoingQuests().removeIf(q -> q.getId().equals(quest.getId()));
    }

    public boolean removeFinishedQuest(Quester quester, Quest quest) {
        return quester.getFinishedQuests().remove(quest.getId()) != null;
    }

    public boolean questerHasTurnedInQuest(Quester quester, String questId) {
        return quester.hasFinishedQuest(questId);
    }

    public boolean questerHasCompletedQuest(Quester quester, String questId) {
        return quester.getOngoingQuests().stream()
                .anyMatch(q -> q.getId().equals(questId) && q.isComplete());
    }

    public <T extends Quest> boolean questerHasQuest(Quester quester, String questId) {
        return quester.getOngoingQuests().stream()
                .anyMatch(q -> q.getId().equals(questId));
    }
}