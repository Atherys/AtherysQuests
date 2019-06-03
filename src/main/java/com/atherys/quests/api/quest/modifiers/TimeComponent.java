package com.atherys.quests.api.quest.modifiers;

import com.atherys.quests.api.quester.Quester;
import com.google.gson.annotations.Expose;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Consumer;

public class TimeComponent {
    @Expose
    private int seconds;

    private LocalDateTime timeStarted;

    private Consumer<Quester> onComplete;

    public TimeComponent(int seconds, Consumer<Quester> onComplete) {
        this.seconds = seconds;
        this.onComplete = onComplete;
    }

    public void setOnComplete(Consumer<Quester> onComplete) {
        this.onComplete = onComplete;
    }

    public void startTiming() {
        this.timeStarted = LocalDateTime.now();
    }

    @Nullable
    public LocalDateTime getTimeStarted() {
        return timeStarted;
    }

    public int getSeconds() {
        return seconds;
    }

    public Optional<Consumer<Quester>> onComplete() {
        return Optional.ofNullable(onComplete);
    }
}
