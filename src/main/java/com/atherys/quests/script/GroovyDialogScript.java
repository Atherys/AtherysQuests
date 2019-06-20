package com.atherys.quests.script;

import com.atherys.quests.api.script.DialogScript;
import com.atherys.script.groovy.GroovyLibrary;
import com.atherys.script.groovy.GroovyScript;

public class GroovyDialogScript extends GroovyScript implements DialogScript {
    protected GroovyDialogScript(String id, String contents) {
        super(id, contents);
    }
}
