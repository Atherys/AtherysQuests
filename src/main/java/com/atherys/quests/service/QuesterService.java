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
import org.spongepowered.api.util.Tuple;

import java.util.*;
import java.util.stream.Collectors;

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
            return getCachedPlayer(quester);
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

        if (!quester.hasFinishedQuest(quest.getId()) && !quester.hasQuest(quest)) {
            quester.addQuest(quest);
            return true;
        } else {
            return false;
        }
    }

    public <T extends Quest> boolean turnInQuest(Quester quester, Quest<T> quest) {
        if (quest.isComplete() && quester.hasQuest(quest)) {

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

    public void removeQuest(Quester quester, Quest quest) {
        quester.removeQuest(quest);
    }
}