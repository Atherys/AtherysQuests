package com.atherys.quests.script.js.lib.location;

import com.atherys.quests.script.js.lib.ScriptFunctions;

import javax.script.ScriptEngine;

public final class LocationFunctions implements ScriptFunctions {
    @Override
    public void put(ScriptEngine engine) {
        engine.put("getWorldFromName", new GetWorldFromName());
        engine.put("locationOf", new LocationOf());
    }
}
