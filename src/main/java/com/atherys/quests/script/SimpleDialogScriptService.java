package com.atherys.quests.script;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.QuestsConfig;
import com.atherys.quests.api.script.DialogScript;
import com.atherys.quests.api.script.DialogScriptService;
import com.atherys.quests.event.dialog.DialogRegistrationEvent;
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
public class SimpleDialogScriptService extends AbstractScriptService<DialogScript> implements DialogScriptService {

    @Inject
    QuestsConfig config;

    SimpleDialogScriptService() {
    }

    @Override
    public DialogScript createScript(String id, String contents) {
        if (config.SCRIPT_TYPE.equals(ScriptConfig.GROOVY_TYPE)) {
            return new GroovyDialogScript(id, contents);
        } else if (config.SCRIPT_TYPE.equals(ScriptConfig.JAVASCRIPT_TYPE)) {
            return new JSDialogScript(id, contents);
        }

        return null;
    }

    @Override
    public void startScripts() {

    }

    @Override
    public void reloadScripts() {
        clearScripts();
        AtherysQuests.getInstance().getDialogService().unregisterDialogs();
        EventHandlerFunction.unregisterAllForType(DialogRegistrationEvent.class);
        try {
            AtherysQuests.getInstance().getDialogScriptService().registerFolder(Paths.get("config/", ID, "/dialogs"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sponge.getEventManager().post(new DialogRegistrationEvent(AtherysQuests.getInstance().getDialogService()));
    }

    @Override
    public void stopScripts() {

    }
}
