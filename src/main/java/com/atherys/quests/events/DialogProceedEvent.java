package com.atherys.quests.events;

import com.atherys.quests.dialog.Dialog;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public class DialogProceedEvent implements Event {

    private Cause cause;

    private Dialog dialog;

    public DialogProceedEvent( Dialog dialog ) {
        this.dialog = dialog;
        this.cause = Cause.builder()
                .append( dialog )
                .append( dialog.getCachedPlayer() )
                .append( dialog.getNPC() )
                .build( Sponge.getCauseStackManager().getCurrentContext() );
    }

    @Override
    public Cause getCause() {
        return cause;
    }

    public Dialog getDialog() {
        return dialog;
    }
}
