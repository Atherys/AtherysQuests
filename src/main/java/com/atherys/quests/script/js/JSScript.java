package com.atherys.quests.script.js;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quest.Script;
import com.atherys.quests.quester.Quester;
import com.google.common.io.Files;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class JSScript implements Script {

    String id;

    String contents;

    public JSScript(String name, String contents) {
        this.id = name;
        this.contents = contents;
    }

    public static JSScript fromFile(File file) throws IOException {
        return new JSScript(file.getName(), Files.toString(file, Charset.defaultCharset()));
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void onLoad(Quest quest) {
        QuestsLib.getInstance().compile(contents, script -> {
            try {
                script.invokeFunction("onLoad", quest);
            } catch (ScriptException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onPickUp(Quest quest, Quester quester) {
        QuestsLib.getInstance().compile(contents, script -> {
            try {
                script.invokeFunction("onPickUp", quest, quester);
            } catch (ScriptException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onProgress(Quest quest, Quester quester) {
        QuestsLib.getInstance().compile(contents, script -> {
            try {
                script.invokeFunction("onProgress", quest, quester);
            } catch (ScriptException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onComplete(Quest quest, Quester quester) {
        QuestsLib.getInstance().compile(contents, script -> {
            try {
                script.invokeFunction("onComplete", quest, quester);
            } catch (ScriptException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onTurnIn(Quest quest, Quester quester) {
        QuestsLib.getInstance().compile(contents, script -> {
            try {
                script.invokeFunction("onTurnIn", quest, quester);
            } catch (ScriptException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }
}
