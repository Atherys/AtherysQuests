package com.atherys.quests.quest.objective;

import com.atherys.quests.quester.Quester;
import ninja.leaping.configurate.objectmapping.Setting;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.text.Text;

public class KillEntityObjective extends AbstractObjective<DestructEntityEvent.Death> {

    @Setting private String entityName;
    @Setting private int requirement;
    @Setting private int progress;

    @Setting
    private boolean complete;

    private KillEntityObjective() {
        super(DestructEntityEvent.Death.class);
    }

    public KillEntityObjective( String entityName, int numberToKill ) {
        super(DestructEntityEvent.Death.class);
        this.entityName = entityName;
        this.requirement = numberToKill;
        this.progress = numberToKill;
    }

    public static KillEntityObjective of ( String entityName, int numberToKill ) {
        return new KillEntityObjective( entityName, numberToKill );
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
    protected void onNotify ( DestructEntityEvent.Death event, Quester quester ) {
        String displayName = event.getTargetEntity().get(Keys.DISPLAY_NAME).orElse( Text.of( event.getTargetEntity().getType().getName() ) ).toPlain();

        if ( displayName.equals(entityName) && progress != 0 ) {
            progress--;
            complete = false;
        } else {
            complete = true;
        }
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public KillEntityObjective copy() {
        return new KillEntityObjective( entityName, requirement );
    }

    @Override
    public Text toText() {
        return Text.of( "Kill ", entityName, ": ", progress, "/", requirement );
    }
}
