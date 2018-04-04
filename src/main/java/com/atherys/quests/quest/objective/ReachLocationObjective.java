package com.atherys.quests.quest.objective;

import com.atherys.quests.api.objective.AbstractObjective;
import com.atherys.quests.quester.Quester;
import com.flowpowered.math.vector.Vector3d;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

public class ReachLocationObjective extends AbstractObjective<MoveEntityEvent> {

    @Expose Text name;

    @Expose private double x;
    @Expose private double y;
    @Expose private double z;
    @Expose private double radius;

    @Expose private boolean complete;

    private ReachLocationObjective () {
        super( MoveEntityEvent.class );
    }

    public ReachLocationObjective ( Text name, Vector3d position, double radius ) {
        this();
        this.name = name;
        this.x = position.getX();
        this.y = position.getY();
        this.z = position.getZ();
        this.radius = radius;
    }

    @Override
    protected void onNotify ( MoveEntityEvent event, Quester quester ) {
        if ( event.getToTransform().getPosition().distance( Vector3d.from( x, y, z ) ) < radius  ) this.complete = true;
    }

    @Override
    public boolean isComplete () {
        return complete;
    }

    @Override
    public ReachLocationObjective copy () {
        return new ReachLocationObjective( name, Vector3d.from( x, y, z ), radius );
    }

    @Override
    public Text toText () {
        return Text.builder().append( Text.of( "Reach ", name ) ).onHover( TextActions.showText( Text.of( "Located @ ", x, ", ", y, ", ", z ) ) ).build();
    }
}
