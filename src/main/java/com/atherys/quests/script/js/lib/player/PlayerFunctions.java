package com.atherys.quests.script.js.lib.player;

import com.atherys.quests.script.js.lib.ScriptFunctions;

import javax.script.ScriptEngine;

public final class PlayerFunctions implements ScriptFunctions {
    @Override
    public void put(ScriptEngine engine) {
        engine.put("getPlayerFromName", new GetPlayerFromName());
        engine.put("getPlayerFromUUID", new GetPlayerFromUUID());
    }
}
