package com.atherys.quests.quest;

import com.google.gson.annotations.Expose;

import java.util.UUID;

/**
 * A Quest which must be delivered to an entity in order to be completed. WIP.
 */
public class DeliverableSimpleQuest extends SimpleQuest {

    @Expose
    private UUID entity;

    public DeliverableSimpleQuest ( String id, int version ) {
        super( id, version );
    }

    private DeliverableSimpleQuest ( DeliverableSimpleQuest quest ) {
        super( quest );
        this.entity = quest.getEntity();

    }

    @Override
    public SimpleQuest copy() {
        return new DeliverableSimpleQuest( this );
    }

    protected void setEntity( UUID uuid ) {
        this.entity = uuid;
    }

    public UUID getEntity () {
        return entity;
    }
}
