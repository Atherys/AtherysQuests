package com.atherys.quests.script;

import com.atherys.quests.api.script.QuestScript;
import com.atherys.quests.api.script.QuestScriptService;
import com.atherys.script.api.AbstractScriptService;

public class SimpleQuestScriptService extends AbstractScriptService<QuestScript> implements QuestScriptService {
    @Override
    public QuestScript createScript(String id, String contents) {
        return new JSQuestScript(id, contents);
    }

    @Override
    public void startScripts() {

    }

    @Override
    public void reloadScripts() {

    }

    @Override
    public void stopScripts() {

    }
}
