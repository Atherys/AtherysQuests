package com.atherys.quests.quest;

import com.atherys.quests.api.quest.DeliverableQuest;
import com.atherys.quests.api.quest.Quest;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;

import java.util.UUID;

/**
 * A {@link StagedQuest} which must be delivered to an entity in order to be completed.
 */
public class DeliverableStagedQuest extends StagedQuest implements DeliverableQuest<StagedQuest> {

    @Expose
    private UUID entity;

    @Expose
    private Text entityName;

    protected DeliverableStagedQuest(String id, int version) {
        super(id, Text.of(), Text.of(), version);
    }

    public DeliverableStagedQuest(StagedQuest quest) {
        super(quest);
    }

    @Override
    public UUID getTarget() {
        return entity;
    }

    @Override
    public Text getTargetName() {
        return name;
    }

    public void setEntity(UUID entity, Text entityName) {
        this.entity = entity;
        this.entityName = entityName;
    }

    @Override
    public Quest<StagedQuest> getQuest() {
        return this;
    }
}
