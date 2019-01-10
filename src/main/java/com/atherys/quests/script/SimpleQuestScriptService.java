package com.atherys.quests.script;

import com.atherys.quests.api.script.QuestScript;
import com.atherys.quests.api.script.QuestScriptService;
import com.atherys.script.api.AbstractScriptService;
import com.atherys.script.js.JSScript;
import com.atherys.script.js.event.JSScriptReloadEvent;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;

@Singleton
public class SimpleQuestScriptService extends AbstractScriptService<QuestScript> implements QuestScriptService {

    SimpleQuestScriptService() {
    }

    @Override
    public QuestScript createScript(String id, String contents) {
        return new JSQuestScript(id, contents);
    }

    @Override
    public void startScripts() {

    }

    @Override
    public void reloadScripts() {
        getScripts().forEach(qs -> {
            JSScriptReloadEvent reloadEvent = new JSScriptReloadEvent((JSScript) qs);
            Sponge.getEventManager().post(reloadEvent);
        });
    }

    @Override
    public void stopScripts() {

    }
}
