package com.atherys.quests.script;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.QuestsConfig;
import com.atherys.quests.api.script.QuestScript;
import com.atherys.quests.api.script.QuestScriptService;
import com.atherys.quests.event.quest.QuestRegistrationEvent;
import com.atherys.script.ScriptConfig;
import com.atherys.script.api.AbstractScriptService;
import com.atherys.script.library.event.EventHandlerFunction;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;

import java.io.IOException;
import java.nio.file.Paths;

import static com.atherys.quests.AtherysQuests.ID;

@Singleton
public class SimpleQuestScriptService extends AbstractScriptService<QuestScript> implements QuestScriptService {

    @Inject
    QuestsConfig config;

    SimpleQuestScriptService() {
    }

    @Override
    public QuestScript createScript(String id, String contents) {
        if (config.SCRIPT_TYPE.equals(ScriptConfig.GROOVY_TYPE)) {
            return new GroovyQuestScript(id, contents);
        } else if (config.SCRIPT_TYPE.equals(ScriptConfig.JAVASCRIPT_TYPE)) {
            return new JSQuestScript(id, contents);
        }

        return null;
    }

    @Override
    public void startScripts() {

    }

    @Override
    public void reloadScripts() {
        clearScripts();
        AtherysQuests.getInstance().getQuestService().unregisterQuests();
        EventHandlerFunction.unregisterAllForType(QuestRegistrationEvent.class);
        try {
            AtherysQuests.getInstance().getQuestScriptService().registerFolder(Paths.get("config/", ID, "/quests"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sponge.getEventManager().post(new QuestRegistrationEvent(AtherysQuests.getInstance().getQuestService()));
    }

    @Override
    public void stopScripts() {

    }
}
