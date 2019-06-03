package com.atherys.quests.service;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quest.modifiers.TimeComponent;
import com.atherys.quests.api.quester.Quester;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Singleton
public class TimedQuestService {
    @Inject
    QuestMessagingService questMsg;

    @Inject
    QuesterService questerService;

    /**
     * This task checks for any completed quests every second.
     */
    private Task timedQuestTask = Task.builder()
            .interval(1L, TimeUnit.SECONDS)
            .execute(() -> {
                LocalDateTime now = LocalDateTime.now();
                List<Quester> questers = Sponge.getServer().getOnlinePlayers().stream()
                        .map(questerService::getQuester)
                        .collect(Collectors.toList());

                questers.forEach(quester -> {
                    quester.getOngoingQuests().forEach(quest -> {
                        if (quest.getTimedComponent().isPresent()) {
                            checkQuest(quest, quester, now);
                        }
                    });
                });
            })
            .submit(AtherysQuests.getInstance());

    /**
     * Checks whether a timed quest time limit is up, and removes the quest from the quester if so.
     */
    private boolean checkQuest(Quest<?> quest, Quester quester, LocalDateTime now) {
        TimeComponent timeComponent = quest.getTimedComponent().get();
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
}
