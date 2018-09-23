package com.atherys.quests.script;

import com.atherys.quests.api.script.QuestScript;
import com.atherys.script.js.JSScript;

public class JSQuestScript extends JSScript implements QuestScript {

    public JSQuestScript(String name, String contents) {
        super(name, contents);
    }
}
