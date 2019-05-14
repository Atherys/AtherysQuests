package com.atherys.quests.api.quest;

import org.spongepowered.api.text.Text;

import java.util.UUID;

public interface DeliverableQuest {
    UUID getTarget();

    Text getTargetName();
}
