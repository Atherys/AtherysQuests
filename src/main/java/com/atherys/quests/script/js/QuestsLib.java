package com.atherys.quests.script.js;

import com.atherys.quests.script.js.lib.item.ItemStackFunctions;
import com.atherys.quests.script.js.lib.text.TextFunctions;

import javax.script.*;
import java.util.function.Consumer;

public final class QuestsLib {

    private static QuestsLib instance = new QuestsLib();

    private ScriptEngine engine;

    private QuestsLib() {
        engine = new ScriptEngineManager().getEngineByName("nashorn");

        new ItemStackFunctions().put(engine);
        new TextFunctions().put(engine);
    }

    public ScriptEngine getEngine() {
        return engine;
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
