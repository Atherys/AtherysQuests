package com.atherys.quests.script.js.lib.util;

import com.atherys.quests.script.js.lib.ScriptFunctions;

import javax.script.ScriptEngine;

public final class UtilityFunctions implements ScriptFunctions {

    @Override
    public void put(ScriptEngine engine) {
        engine.put("uuidOf", new UUIDOf());
        engine.put("randomUUID", new RandomUUID());
    }
}
