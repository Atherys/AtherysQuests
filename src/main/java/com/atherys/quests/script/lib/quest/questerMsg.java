package com.atherys.quests.script.lib.quest;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class questerMsg extends TwoArgFunction {

    @Override
    public LuaValue call(LuaValue arg1, LuaValue arg2) {
        // first arg is going to be the uuid, as a string, of the player
        // second arg is going to be the string message
        return null;
    }

}
