package com.atherys.quests.quest.objective;

import com.atherys.quests.api.objective.AbstractObjective;
import com.atherys.quests.quester.Quester;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * An objective for reaching a {@link Location} within a given radius.
 */
public class ReachLocationObjective extends AbstractObjective<MoveEntityEvent> {

    @Expose Text name;

    @Expose private Location<World> location;
    @Expose private double radius;

    @Expose private boolean complete;

    private ReachLocationObjective () {
        super( MoveEntityEvent.class );
    }

    ReachLocationObjective ( Text name, Location<World> location, double radius ) {
        this();
        this.name = name;
        this.location = location;
        this.radius = radius;
    }

    @Override
    protected void onNotify ( MoveEntityEvent event, Quester quester ) {
        if ( event.getToTransform().getLocation().getExtent().equals( location.getExtent() ) ) {
            if ( event.getToTransform().getPosition().distance( location.getPosition() ) < radius ) {
                this.complete = true;
            }
        }
    }

    @Override
    public boolean isComplete () {
        return complete;
    }

    @Override
    public ReachLocationObjective copy () {
        return new ReachLocationObjective( name, location, radius );
    }

    @Override
    public Text toText () {
        return Text.builder().append( Text.of( "Reach ", name ) ).onHover( TextActions.showText( Text.of( "Located @ ", location.getPosition() ) ) ).build();
    }
}
