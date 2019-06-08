package com.atherys.quests.api.quest.modifiers;

import com.atherys.quests.api.base.Prototype;
import com.atherys.quests.api.quester.Quester;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Consumer;

public class TimeComponent implements Prototype<TimeComponent> {
    @Expose
    private int seconds;

    private Instant timeStarted;

    private Consumer<Player> onComplete;

    public TimeComponent(int seconds, Consumer<Player> onComplete) {
        this.seconds = seconds;
        this.onComplete = onComplete;
    }

    public void setOnComplete(Consumer<Player> onComplete) {
        this.onComplete = onComplete;
    }

    public void startTiming() {
        this.timeStarted = Instant.now();
    }

    @Nullable
    public Instant getTimeStarted() {
        return timeStarted;
    }

    public int getSeconds() {
        return seconds;
    }

    public Optional<Consumer<Player>> onComplete() {
        return Optional.ofNullable(onComplete);
    }

    @Override
    public TimeComponent copy() {
        return new TimeComponent(seconds, onComplete);
    }
}
