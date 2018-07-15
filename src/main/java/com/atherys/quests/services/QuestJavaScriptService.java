package com.atherys.quests.services;

import com.atherys.quests.api.script.QuestScript;
import com.atherys.quests.api.script.QuestScriptService;
import com.atherys.quests.script.JSQuestScript;

import java.io.File;
import java.io.IOException;

public class QuestJavaScriptService extends AbstractJavaScriptService<QuestScript> implements QuestScriptService {

    private static QuestJavaScriptService instance = new QuestJavaScriptService();

    private QuestJavaScriptService() {
    }

    @Override
    public QuestScript fromFile(File file) throws IOException {
        return JSQuestScript.fromFile(file);
    }

    public static QuestJavaScriptService getInstance() {
        return instance;
    }
}
