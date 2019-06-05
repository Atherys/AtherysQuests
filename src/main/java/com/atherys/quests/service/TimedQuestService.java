package com.atherys.quests.service;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quest.modifiers.TimeComponent;
import com.atherys.quests.api.quester.Quester;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.boss.BossBarColors;
import org.spongepowered.api.boss.BossBarOverlays;
import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.spongepowered.api.text.format.TextColors.GOLD;
import static org.spongepowered.api.text.format.TextColors.GREEN;

/**
 * Keeps track of timed quests and displaying them to {@link Quester}s.
 */
@Singleton
public class TimedQuestService {
    @Inject
    QuestMessagingService questMsg;

    @Inject
    QuesterService questerService;

    private Map<UUID, ServerBossBar> questTimers = new HashMap<>();

    private ServerBossBar.Builder barTemplate = ServerBossBar.builder()
            .color(BossBarColors.GREEN)
            .overlay(BossBarOverlays.PROGRESS);

    /**
     * This task checks for any completed quests every second.
     */
    private Task timedQuestTask = Task.builder()
            .interval(1L, TimeUnit.SECONDS)
            .execute(() -> {
                Instant now = Instant.now();
                List<Quester> questers = Sponge.getServer().getOnlinePlayers().stream()
                        .map(questerService::getQuester)
                        .collect(Collectors.toList());

                questers.forEach(quester -> {
                    quester.getOngoingQuests().forEach(quest -> {
                        if (quest.getTimedComponent().isPresent()) {
                            checkQuest(quest, quester, now);
                            updateTimerDisplay(quest, quester, now);
                        }
                    });
                });
            })
            .submit(AtherysQuests.getInstance());

    /**
     * Checks whether a timed quest time limit is up, and removes the quest from the quester if so.
     */
    private void checkQuest(Quest<?> quest, Quester quester, Instant now) {
        TimeComponent timeComponent = quest.getTimedComponent().get();
        Instant time = timeComponent.getTimeStarted();
        if (time == null) {
            return;
        }
        long seconds = timeComponent.getSeconds();
        Instant timestampPlus = time.plus(seconds, ChronoUnit.SECONDS);

        if (now.compareTo(timestampPlus) >= 0) {
            failTimedQuest(quest, quester);
        }
    }

    private void failTimedQuest(Quest<?> quest, Quester quester) {
        questMsg.error(quester, "You have failed the quest \"", quest.getName(), "\"!");
        questerService.removeQuest(quester, quest);
        quest.getTimedComponent().get().onComplete().ifPresent(onComplete -> onComplete.accept(quester));
        stopDisplayingTimer(quester);
    }

    private void updateTimerDisplay(Quest<?> quest, Quester quester, Instant now) {
        TimeComponent timeComponent = quest.getTimedComponent().get();
        long secondsPassed = now.getEpochSecond() - timeComponent.getTimeStarted().getEpochSecond();
        int totalSeconds = quest.getTimedComponent().get().getSeconds();
        long secondsLeft = (totalSeconds - secondsPassed);

        if (secondsLeft <= 0) {
            stopDisplayingTimer(quester);
            return;
        }

        float percentLeft = (float) secondsLeft / totalSeconds;

        Text barText = Text.of(GOLD, quest.getName(), " - ", GREEN, secondsLeft);

        ServerBossBar bar = questTimers.get(quester.getUniqueId());

        if (bar == null) {
            bar = barTemplate
                    .name(barText)
                    .percent(percentLeft)
                    .build()
                    .addPlayer(questerService.getPlayer(quester));
            questTimers.put(quester.getUniqueId(), bar);
        } else {
            bar.setName(barText).setPercent(percentLeft);
        }
    }

    /**
     * Stops showing a quest timer for a given quester.
     * @param quester The quester.
     */
    public void stopDisplayingTimer(Quester quester) {
        ServerBossBar bar = questTimers.remove(quester.getUniqueId());
        if (bar != null) {
            bar.removePlayer(questerService.getPlayer(quester));
        }
    }
}
