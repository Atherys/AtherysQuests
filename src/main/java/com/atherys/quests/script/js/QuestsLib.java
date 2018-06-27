package com.atherys.quests.script.js;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.script.js.lib.block.BlockFunctions;
import com.atherys.quests.script.js.lib.item.ItemStackFunctions;
import com.atherys.quests.script.js.lib.location.LocationFunctions;
import com.atherys.quests.script.js.lib.player.PlayerFunctions;
import com.atherys.quests.script.js.lib.task.TaskFunctions;
import com.atherys.quests.script.js.lib.text.TextFunctions;
import com.atherys.quests.script.js.lib.util.UtilityFunctions;

import javax.script.*;
import java.util.function.Consumer;

public final class QuestsLib {

    private static QuestsLib instance = new QuestsLib();

    private ScriptEngine engine;

    private QuestsLib() {
        engine = new ScriptEngineManager().getEngineByName("nashorn");

        new ItemStackFunctions().put(engine);
        new TextFunctions().put(engine);
        new PlayerFunctions().put(engine);
        new LocationFunctions().put(engine);
        new TaskFunctions().put(engine);
        new BlockFunctions().put(engine);
        new UtilityFunctions().put(engine);

        engine.put("QUESTS_PLUGIN", AtherysQuests.getInstance());
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
