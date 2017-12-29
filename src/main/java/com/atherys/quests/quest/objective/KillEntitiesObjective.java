package com.atherys.quests.quest.objective;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.text.Text;

import java.util.HashMap;
import java.util.Map;

public class KillEntitiesObjective extends AbstractObjective<DestructEntityEvent.Death> {

    private Map<String,Integer> requirements = new HashMap<>();
    private Map<String,Integer> progress = new HashMap<>();

    protected KillEntitiesObjective( Map<String,Integer> mobsToKill ) {
        super( DestructEntityEvent.Death.class );
        this.requirements = mobsToKill;
        this.progress = new HashMap<>( mobsToKill );
    }

    @Override
    protected void onNotify ( DestructEntityEvent.Death event ) {
        String displayName = event.getTargetEntity().get(Keys.DISPLAY_NAME).orElse( Text.of( event.getTargetEntity().getType().getName() ) ).toPlain();
        if ( progress.containsKey( displayName ) && progress.get( displayName ) != 0 ) {
            progress.put( displayName, progress.get( displayName ) - 1 );
        }
    }

    @Override
    public boolean isComplete() {
        for (Map.Entry<String, Integer> entry : progress.entrySet()) {
            if ( entry.getValue() != 0 ) return false;
        }
        return true;
    }

    @Override
    public KillEntitiesObjective copy() {
        return new KillEntitiesObjective( new HashMap<>( this.requirements ) );
    }

    public Map<String, Integer> getRequirements() {
        return requirements;
    }

    public Map<String,Integer> getProgress() {
        return progress;
    }

}
