package com.atherys.quests.services;

import com.atherys.quests.api.script.DialogScript;
import com.atherys.quests.api.script.DialogScriptService;
import com.atherys.quests.script.JSDialogScript;

import java.io.File;
import java.io.IOException;

public class DialogJavaScriptService extends AbstractJavaScriptService<DialogScript> implements DialogScriptService {

    private static DialogJavaScriptService instance = new DialogJavaScriptService();

    private DialogJavaScriptService() {
    }

    @Override
    public DialogScript fromFile(File file) throws IOException {
        return JSDialogScript.fromFile(file);
    }

    public static DialogJavaScriptService getInstance() {
        return instance;
    }
}
