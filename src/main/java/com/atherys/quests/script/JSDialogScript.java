package com.atherys.quests.script;

import com.atherys.core.AtherysCore;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.script.DialogScript;
import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogTree;
import com.atherys.quests.quester.Quester;
import com.google.common.io.Files;
import org.spongepowered.api.entity.Entity;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class JSDialogScript extends AbstractJSScript implements DialogScript {

    protected JSDialogScript(String id, String content) {
        super(id, content);
    }

    public static JSDialogScript fromFile(File file) throws IOException {
        return new JSDialogScript(file.getName(), Files.toString(file, Charset.defaultCharset()));
    }

    @Override
    public void start() {
        AtherysCore.getScriptingEngine().compile(contents, script -> {
            try {
                DialogTree dialogTree = (DialogTree) script.invokeFunction("onStartServer");
                AtherysQuests.getDialogManager().registerDialog(dialogTree);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onBegin(Quester quester, Entity entity, DialogTree tree) {
        AtherysCore.getScriptingEngine().compile(contents, script -> {
            try {
                script.invokeFunction("onBeginDialog", quester, entity, tree);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onProgress(Quester quester, Entity entity, DialogTree tree, DialogNode node) {
        AtherysCore.getScriptingEngine().compile(contents, script -> {
            try {
                script.invokeFunction("onProgressDialog", quester, entity, tree, node);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onEnd(Quester quester, Entity entity, DialogTree tree, DialogNode node) {
        AtherysCore.getScriptingEngine().compile(contents, script -> {
            try {
                script.invokeFunction("onEndDialog", quester, entity, tree, node);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void stop() {
        AtherysCore.getScriptingEngine().compile(contents, script -> {
            try {
                script.invokeFunction("onStopServer");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
