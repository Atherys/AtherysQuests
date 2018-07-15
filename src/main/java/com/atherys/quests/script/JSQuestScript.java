package com.atherys.quests.script;

import com.atherys.core.AtherysCore;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.script.QuestScript;
import com.atherys.quests.quester.Quester;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class JSQuestScript extends AbstractJSScript implements QuestScript {

    public JSQuestScript(String name, String contents) {
        super(name, contents);
    }

    public static JSQuestScript fromFile(File file) throws IOException {
        return new JSQuestScript(file.getName(), Files.toString(file, Charset.defaultCharset()));
    }

    @Override
    public void start() {
        AtherysCore.getScriptingEngine().compile(contents, script -> {
            try {
                Quest quest = (Quest) script.invokeFunction("onStartServer");
                quest.setScript(this);

                AtherysQuests.getQuestManager().registerQuest(quest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onBegin(Quest quest, Quester quester) {
        AtherysCore.getScriptingEngine().compile(contents, script -> {
            try {
                script.invokeFunction("onBegin", quest, quester);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onPickUp(Quest quest, Quester quester) {
        AtherysCore.getScriptingEngine().compile(contents, script -> {
            try {
                script.invokeFunction("onPickUp", quest, quester);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onProgress(Quest quest, Quester quester, Objective objective) {
        AtherysCore.getScriptingEngine().compile(contents, script -> {
            try {
                script.invokeFunction("onProgress", quest, quester, objective);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onComplete(Quest quest, Quester quester) {
        AtherysCore.getScriptingEngine().compile(contents, script -> {
            try {
                script.invokeFunction("onComplete", quest, quester);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onTurnIn(Quest quest, Quester quester) {
        AtherysCore.getScriptingEngine().compile(contents, script -> {
            try {
                script.invokeFunction("onTurnIn", quest, quester);
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
