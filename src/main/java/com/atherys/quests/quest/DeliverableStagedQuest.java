package com.atherys.quests.quest;

import org.spongepowered.api.text.Text;

/**
 * A {@link StagedQuest} which must be delivered to an entity in order to be completed.
 */
public class DeliverableStagedQuest extends StagedQuest {

    protected DeliverableStagedQuest(String id, int version) {
        super(id, Text.of(), Text.of(), version);
    }

}
