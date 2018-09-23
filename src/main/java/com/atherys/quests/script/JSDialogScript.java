package com.atherys.quests.script;

import com.atherys.quests.api.script.DialogScript;
import com.atherys.script.js.JSScript;

public class JSDialogScript extends JSScript implements DialogScript {

    protected JSDialogScript(String id, String content) {
        super(id, content);
    }

}
