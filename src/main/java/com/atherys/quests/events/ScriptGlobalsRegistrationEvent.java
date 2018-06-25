package com.atherys.quests.events;

import com.atherys.quests.script.QuestsLib;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public class ScriptGlobalsRegistrationEvent implements Event {

    private Cause cause;

    private QuestsLib lib;

    public ScriptGlobalsRegistrationEvent() {
        cause = Cause.builder()
                .append(QuestsLib.get())
                .build(Sponge.getCauseStackManager().getCurrentContext());

        lib = QuestsLib.get();
    }

    public QuestsLib getLib() {
        return lib;
    }

    @Override
    public Cause getCause() {
        return cause;
    }
}
