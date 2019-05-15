package com.atherys.quests.quest;

import com.atherys.quests.api.quest.DeliverableQuest;
import com.atherys.quests.api.quest.Quest;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;

import java.util.UUID;

/**
 * A {@link SimpleQuest} which must be delivered to an entity in order to be completed.
 */
public class DeliverableSimpleQuest extends SimpleQuest implements DeliverableQuest {

    @Expose
    private UUID entity;

    @Expose
    private Text entityName;

    public DeliverableSimpleQuest(String id, int version) {
        super(id, version);
    }

    private DeliverableSimpleQuest(DeliverableSimpleQuest quest) {
        this(quest.id, quest.version);
        this.entity = quest.getTarget();
        this.entityName = quest.getTargetName();
    }

    public DeliverableSimpleQuest(SimpleQuest quest) {
        super(quest);
    }

    @Override
    public SimpleQuest copy() {
        return new DeliverableSimpleQuest(this);
    }

    public void setEntity(UUID uuid, Text entityName) {
        this.entity = uuid;
        this.entityName = entityName;
    }

    @Override
    public UUID getTarget() {
        return entity;
    }

    @Override
    public Text getTargetName() {
        return entityName;
    }

    @Override
    public Quest getQuest() {
        return this;
    }
}
