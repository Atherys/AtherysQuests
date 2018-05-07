package com.atherys.quests.quest;

import com.atherys.quests.quester.Quester;
import com.google.gson.annotations.Expose;

import java.util.UUID;

/**
 * A {@link SimpleQuest} which must be delivered to an entity in order to be completed.
 * This is a Placeholder class which does not contain any relevant implementation.
 * The feature of deliverable quests is still a work in progress and not yet functional.
 */
public class DeliverableSimpleQuest extends SimpleQuest {

    @Expose
    private UUID entity;

    public DeliverableSimpleQuest(String id, int version) {
        super(id, version);
    }

    private DeliverableSimpleQuest(DeliverableSimpleQuest quest) {
        super(quest);
        this.entity = quest.getEntity();
    }

    @Override
    public void pickUp(Quester quester) {

    }

    @Override
    public SimpleQuest copy() {
        return new DeliverableSimpleQuest(this);
    }

    protected void setEntity(UUID uuid) {
        this.entity = uuid;
    }

    public UUID getEntity() {
        return entity;
    }
}
