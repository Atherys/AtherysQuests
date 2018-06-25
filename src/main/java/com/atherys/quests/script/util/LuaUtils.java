package com.atherys.quests.script.util;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;

public final class LuaUtils {

    public static LuaValue callFunction(LuaValue globals, String function, LuaValue... args) {
        LuaValue func = globals.get(function);
        if ( func != null && !func.isnil() && func.isfunction() ) {
            LuaFunction callable = func.checkfunction();
            return (LuaValue) callable.invoke(args);
        }
        return LuaValue.NIL;
    }

}
