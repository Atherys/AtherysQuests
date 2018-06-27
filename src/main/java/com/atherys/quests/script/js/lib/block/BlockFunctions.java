package com.atherys.quests.script.js.lib.block;

import com.atherys.quests.script.js.lib.ScriptFunctions;

import javax.script.ScriptEngine;

public final class BlockFunctions implements ScriptFunctions {
    @Override
    public void put(ScriptEngine engine) {
        engine.put("blockOf", new BlockOf());
        engine.put("getBlockFromLocation", new GetBlockFromLocation());
        engine.put("setBlockAtLocation", new SetBlockAtLocation());
    }
}
