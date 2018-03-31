package com.atherys.quests.quest.objective;

import com.atherys.quests.api.objective.AbstractObjective;
import com.atherys.quests.quester.Quester;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

public class InteractWithBlockObjective extends AbstractObjective<InteractBlockEvent> {

    @Expose
    private BlockSnapshot snapshot;

    @Expose
    private boolean complete = false;

    private InteractWithBlockObjective () {
        super( InteractBlockEvent.class );
    }

    public InteractWithBlockObjective ( BlockSnapshot snapshot ) {
        this();
        this.snapshot = snapshot;
    }

    @Override
    protected void onNotify ( InteractBlockEvent event, Quester quester ) {
        if ( event.getTargetBlock().equals( this.snapshot ) ) this.complete = true;
    }

    @Override
    public boolean isComplete () {
        return complete;
    }

    @Override
    public InteractWithBlockObjective copy () {
        return new InteractWithBlockObjective( snapshot );
    }

    @Override
    public Text toText () {
        return Text.builder().append( Text.of( "Interact With ", snapshot.getState().getType().getName() ) ).onHover( TextActions.showText( Text.of( "Located at ", snapshot.getPosition() ) ) ).build();
    }
}
