package com.atherys.quests.script;

import com.atherys.quests.api.script.QuestScript;
import com.atherys.script.api.AbstractScript;
import com.atherys.script.js.JavaScriptLibrary;

public class JSQuestScript extends AbstractScript implements QuestScript {

    public JSQuestScript(String name, String contents) {
        super(name, contents, JavaScriptLibrary.getInstance());
    }
}
