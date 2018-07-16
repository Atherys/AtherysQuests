package com.atherys.quests.script;

import com.atherys.quests.api.script.DialogScript;
import com.atherys.script.api.AbstractScript;
import com.atherys.script.js.JavaScriptLibrary;

public class JSDialogScript extends AbstractScript implements DialogScript {

    protected JSDialogScript(String id, String content) {
        super(id, content, JavaScriptLibrary.getInstance());
    }

}
