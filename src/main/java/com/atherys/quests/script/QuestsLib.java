package com.atherys.quests.script;

import com.atherys.quests.events.ScriptGlobalsRegistrationEvent;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.spongepowered.api.Sponge;

import java.util.HashMap;
import java.util.Map;

public class QuestsLib extends TwoArgFunction {

    private static QuestsLib instance = new QuestsLib();

    private Map<String, LuaValue> globals = new HashMap<>();

    private QuestsLib() {
    }

    public void registerGlobals() {
        ScriptGlobalsRegistrationEvent event = new ScriptGlobalsRegistrationEvent();
        Sponge.getEventManager().post(event);
    }

    /**
     * Sets the identifier to the provided value, without checking for availability
     *
     * @param identifier The identifier to set
     * @param value The value to set
     */
    public void setGlobal(String identifier, LuaValue value) {
        globals.put(identifier, value);
    }

    /**
     * Adds a new value to the global scope, if not already present.
     *
     * @param identifier The identifier to use
     * @param value The value to place
     * @return true if no other such identifier was used, false is a collision was detected
     */
    public boolean addGlobal(String identifier, LuaValue value) {
        boolean result = globals.containsKey(identifier);

        if (!result) globals.put(identifier, value);

        return result;
    }

    /**
     * Removes whatever was set to the given identifier from the global scope
     *
     * @param identifier The identifier to remove
     * @return Whether the identifier was removed or not. If no such value existed, this will return false.
     */
    public boolean removeGlobal(String identifier) {
        return globals.remove(identifier) != null;
    }

    /**
     * Copied from {@link org.luaj.vm2.lib.BaseLib}<br><br>
     *
     * Perform one-time initialization on the library by adding base functions
     * to the supplied environment, and returning it as the return value.
     * @param modname the module name supplied if this is loaded via 'require'.
     * @param environment the environment to load into, which must be a Globals instance.
     */
    @Override
    public LuaValue call(LuaValue modname, LuaValue environment) {
        globals.forEach(environment::set);
        return environment;
    }

    public static QuestsLib get() {
        return instance;
    }
}
