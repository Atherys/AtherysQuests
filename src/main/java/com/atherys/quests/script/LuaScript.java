package com.atherys.quests.script;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quest.Script;
import com.atherys.quests.managers.LuaScriptManager;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.script.util.LuaUtils;
import com.google.common.io.Files;
import org.luaj.vm2.LuaValue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class LuaScript implements Script {

    private String id;

    private String script;

    private LuaScript(String id, String script) {
        this.id = id;
        this.script = script;
    }

    public static LuaScript fromFile(File file) throws IOException {
        return new LuaScript(file.getName(), Files.toString(file, Charset.defaultCharset()));
    }

    @Override
    public String getId() {
        return id;
    }

    private LuaValue call(String function, LuaValue... args) {
        LuaValue globals = LuaScriptManager.getInstance().getGlobals();
        LuaValue wholeScript = globals.get("dofile").call(LuaValue.valueOf(script));
        return LuaUtils.callFunction(wholeScript, function, args);
    }

    @Override
    public void onLoad(Quest quest) {
        call("onQuestLoad", LuaValue.valueOf(quest.getId()));
    }

    @Override
    public void onPickUp(Quest quest, Quester quester) {
        call("onQuestPickUp", LuaValue.valueOf(quest.getId()), LuaValue.valueOf(quester.getUUID().toString()));
    }

    @Override
    public void onProgress(Quest quest, Quester quester) {
        call("onQuestProgress", LuaValue.valueOf(quest.getId()), LuaValue.valueOf(quester.getUUID().toString()));
    }

    @Override
    public void onComplete(Quest quest, Quester quester) {
        call("onQuestComplete", LuaValue.valueOf(quest.getId()), LuaValue.valueOf(quester.getUUID().toString()));
    }

    @Override
    public void onTurnIn(Quest quest, Quester quester) {
        call("onQuestTurnIn", LuaValue.valueOf(quest.getId()), LuaValue.valueOf(quester.getUUID().toString()));
    }

}
