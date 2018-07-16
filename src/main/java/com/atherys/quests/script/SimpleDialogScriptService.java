package com.atherys.quests.script;

import com.atherys.quests.api.script.DialogScript;
import com.atherys.quests.api.script.DialogScriptService;
import com.atherys.script.api.AbstractScriptService;

public class SimpleDialogScriptService extends AbstractScriptService<DialogScript> implements DialogScriptService {

    private SimpleDialogScriptService instance = new SimpleDialogScriptService();

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

    public SimpleDialogScriptService getInstance() {
        return instance;
    }
}
