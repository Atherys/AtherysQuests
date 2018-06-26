package com.atherys.quests.events;

import com.atherys.quests.script.js.QuestsLib;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public class ScriptContextCreationEvent implements Event {

    private Cause cause;

    private QuestsLib lib;

    public ScriptContextCreationEvent() {
        cause = Cause.builder()
                .append(QuestsLib.getInstance())
                .build(Sponge.getCauseStackManager().getCurrentContext());

        lib = QuestsLib.getInstance();
    }

    public QuestsLib getLib() {
        return lib;
    }

    @Override
    public Cause getCause() {
        return cause;
    }
}
