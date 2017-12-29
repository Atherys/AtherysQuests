package com.atherys.quests.quest.objective;

import com.atherys.quests.quester.Quester;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.text.Text;

import java.util.HashMap;
import java.util.Map;

public class KillEntitiesObjective extends AbstractObjective<DestructEntityEvent.Death> {

    private Map<String,Integer> requirements = new HashMap<>();
    private Map<String,Integer> progress = new HashMap<>();

    private boolean complete;

    public KillEntitiesObjective(Map<String, Integer> mobsToKill) {
        super( DestructEntityEvent.Death.class );
        this.requirements = mobsToKill;
        this.progress = new HashMap<>( mobsToKill );
    }

    public static KillEntitiesObjective of ( String entityName, int numberToKill ) {
        Map<String,Integer> reqs = new HashMap<>();
        reqs.put( entityName, numberToKill );
        return new KillEntitiesObjective( reqs );
    }

    @Override
    protected void onNotify ( DestructEntityEvent.Death event, Quester quester ) {
        String displayName = event.getTargetEntity().get(Keys.DISPLAY_NAME).orElse( Text.of( event.getTargetEntity().getType().getName() ) ).toPlain();
        if ( progress.containsKey( displayName ) && progress.get( displayName ) != 0 ) {
            progress.put( displayName, progress.get( displayName ) - 1 );
        }

        // Update complete status
        complete = true;
        for (Map.Entry<String, Integer> entry : progress.entrySet()) {
            if ( entry.getValue() != 0 ) complete = false;
        }
    }

    @Override
    public boolean isComplete() {
        return complete;
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
