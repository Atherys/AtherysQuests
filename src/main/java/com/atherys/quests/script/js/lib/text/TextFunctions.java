package com.atherys.quests.script.js.lib.text;

import com.atherys.quests.script.js.lib.ScriptFunctions;

import javax.script.ScriptEngine;

public final class TextFunctions implements ScriptFunctions {
    @Override
    public void put(ScriptEngine engine) {
        engine.put("textOf", new TextOf());
        engine.put("color", new Color());
        engine.put("style", new Style());
    }
}
