package com.atherys.quests.script;

import com.atherys.quests.api.script.DialogScript;
import com.atherys.quests.api.script.DialogScriptService;
import com.atherys.script.api.AbstractScriptService;
import com.google.inject.Singleton;

@Singleton
public class SimpleDialogScriptService extends AbstractScriptService<DialogScript> implements DialogScriptService {

    private static SimpleDialogScriptService instance = new SimpleDialogScriptService();

    @Override
    public DialogScript createScript(String id, String contents) {
        return new JSDialogScript(id, contents);
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

    public static SimpleDialogScriptService getInstance() {
        return instance;
    }
}
