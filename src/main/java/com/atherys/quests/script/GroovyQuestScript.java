package com.atherys.quests.script;

import com.atherys.quests.api.script.QuestScript;
import com.atherys.script.groovy.GroovyLibrary;
import com.atherys.script.groovy.GroovyScript;

public class GroovyQuestScript extends GroovyScript implements QuestScript {
    protected GroovyQuestScript(String id, String contents) {
        super(id, contents);
    }
}
