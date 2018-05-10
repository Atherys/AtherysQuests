package com.atherys.quests.quest.objective;

import com.atherys.quests.api.objective.AbstractObjective;
import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.quester.Quester;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.text.Text;

/**
 * A simple {@link Objective} for killing entities by name. If the entity is nameless, then it's {@link EntityType#getName()} will be used instead.
 */
public class KillEntityObjective extends AbstractObjective<DestructEntityEvent.Death> {

    @Expose
    private String entityName;
    @Expose
    private int requirement;
    @Expose
    private int progress;

    private KillEntityObjective(){
        super(DestructEntityEvent.Death.class);
    }

    KillEntityObjective(String entityName, int numberToKill) {
        this();
        this.entityName = entityName;
        this.requirement = numberToKill;
        this.progress = numberToKill;
    }

    public String getEntityName() {
        return entityName;
    }

    public int getRequirement() {
        return requirement;
    }

    public int getProgress() {
        return progress;
    }

    @Override
    protected void onNotify(DestructEntityEvent.Death event, Quester quester) {
        String displayName = event.getTargetEntity().get(Keys.DISPLAY_NAME).orElse(Text.of(event.getTargetEntity().getType().getName())).toPlain();

        if(!displayName.equals(entityName)) return;

        if(progress != 0) progress--;
    }

    @Override
    public boolean isComplete() {
        return progress == 0;
    }

    @Override
    public KillEntityObjective copy() {
        return new KillEntityObjective(entityName, requirement);
    }

    @Override
    public Text toText() {
        return Text.of("Kill ", entityName, ": ", requirement - progress, "/", requirement);
    }
}
