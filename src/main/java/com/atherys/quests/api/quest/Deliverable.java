package com.atherys.quests.api.quest;

import org.spongepowered.api.text.Text;

import java.util.UUID;

public interface Deliverable {
    UUID getTarget();

    Text getTargetName();
}
