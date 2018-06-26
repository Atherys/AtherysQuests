package com.atherys.quests.script.js;

import com.atherys.quests.script.js.hook.QuestsHook;
import com.atherys.quests.script.js.hook.ScriptHook;
import com.atherys.quests.script.js.hook.SpongeHook;

import javax.script.*;
import java.util.function.Consumer;

public final class QuestsLib {

    private static QuestsLib instance = new QuestsLib();

    private ScriptEngine engine;
    private ScriptContext context;

    private QuestsLib() {
        engine = new ScriptEngineManager().getEngineByName("nashorn");
        context = new SimpleScriptContext();

        addHook("sponge", new SpongeHook());
        addHook("quests", new QuestsHook());

        Bindings bindings = new SimpleBindings();

        engine.setContext(context);
    }

    public ScriptEngine getEngine() {
        return engine;
    }

    public ScriptContext getContext() {
        return context;
    }

    public void addHook(String attrib, ScriptHook hook) {
        hook.registerHooks(getContext());
        context.setAttribute(attrib, hook, ScriptContext.GLOBAL_SCOPE);
    }

    public void compile(String script, Consumer<Invocable> consumer) {
        try {
            consumer.accept((Invocable) engine.eval(script));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public static QuestsLib getInstance() {
        return instance;
    }

}
