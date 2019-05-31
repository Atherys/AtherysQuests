package com.atherys.quests.service;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quest.modifiers.Timeable;
import com.atherys.quests.api.quester.Quester;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Tuple;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Singleton
public class TimedQuestService {
    @Inject
    QuestMessagingService questMsg;

    @Inject
    QuesterService questerService;

    private Set<Tuple<Quester, Quest<?>>> ongoingTimedQuests = new HashSet<>();

    private Set<Tuple<Quester, Quest<?>>> toAdd = new HashSet<>();

    private Set<Tuple<Quester, Quest<?>>> toRemove = new HashSet<>();

    /**
     * This task checks for any completed quests every second.
     */
    private Task timedQuestTask = Task.builder()
            .interval(1L, TimeUnit.SECONDS)
            .execute(() -> {
                LocalDateTime now = LocalDateTime.now();
                ongoingTimedQuests.forEach(questerQuestTuple -> {
                    if (checkQuest(questerQuestTuple.getSecond(), questerQuestTuple.getFirst(), now)) {
                        toRemove.add(questerQuestTuple);
                    }
                });
                ongoingTimedQuests.removeAll(toRemove);
                toRemove.clear();

                ongoingTimedQuests.addAll(toAdd);
                toAdd.clear();
            })
            .submit(AtherysQuests.getInstance());

    /**
     * Checks whether a timed quest time limit is up, and removes the quest from the quester if so.
     */
    private boolean checkQuest(Quest<?> quest, Quester quester, LocalDateTime now) {
        Timeable timeComponent = quest.getTimedComponent().get();
        LocalDateTime time = timeComponent.getTimeStarted();
        long seconds = timeComponent.getSeconds();
        LocalDateTime timestampPlus = time.plus(seconds, ChronoUnit.SECONDS);

        if (now.compareTo(timestampPlus) >= 0) {
            questMsg.error(quester, "You have failed the quest \"", quest.getName(), "\"!");
            questerService.removeQuest(quester, quest);
            timeComponent.onComplete().ifPresent(onComplete -> onComplete.accept(quester));
            return true;
        }
        return false;
    }

    /**
     * Adds a {@link Quest}/{@link Quester} pair to be checked.
     */
    public void startTimingQuest(Quest<?> quest, Quester quester) {
        toAdd.add(new Tuple<>(quester, quest));
    }

    /**
     * Stops a {@link Quest}/{@link Quester} pair from being checked preemptively.
     */
    public void stopTimingQuest(Quest<?> quest, Quester quester) {
        toRemove.add(new Tuple<>(quester, quest));
    }
}
