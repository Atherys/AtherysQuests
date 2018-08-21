package com.atherys.quests.quest;

import org.spongepowered.api.text.Text;

/**
 * A {@link StagedQuest} which must be delivered to an entity in order to be completed.
 * This is a Placeholder class which does not contain any relevant implementation.
 * The feature of deliverable quests is still a work in progress and not yet functional.
 */
public class DeliverableStagedQuest extends StagedQuest {

    protected DeliverableStagedQuest(String id, int version) {
        super(id, Text.of(), Text.of(), version);
    }

}
