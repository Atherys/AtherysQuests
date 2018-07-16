package com.atherys.quests.script.lib;

import com.atherys.quests.event.*;
import com.atherys.script.api.library.LibraryExtension;
import com.atherys.script.api.library.ScriptLibrary;
import com.atherys.script.js.library.event.EventHandlerFunction;

public class QuestExtension implements LibraryExtension {

    private static QuestExtension instance = new QuestExtension();

    private QuestExtension() {
    }

    @Override
    public void extend(ScriptLibrary scriptLibrary) {
        scriptLibrary.put("onDialogRegistration", new EventHandlerFunction<>(DialogRegistrationEvent.class));
        scriptLibrary.put("onDialogStart", );
        scriptLibrary.put("onDialogProgress", new EventHandlerFunction<>(DialogProceedEvent.class));
        scriptLibrary.put("onDialogComplete", );

        scriptLibrary.put("onQuestRegistration", new EventHandlerFunction<>(QuestRegistrationEvent.class));
        scriptLibrary.put("onQuestStart", new EventHandlerFunction<>(QuestStartedEvent.class));
        scriptLibrary.put("onQuestProgress", );
        scriptLibrary.put("onQuestComplete", new EventHandlerFunction<>(QuestCompletedEvent.class));
        scriptLibrary.put("onQuestTurnIn", new EventHandlerFunction<>(QuestTurnedInEvent.class));
    }

    public static QuestExtension getInstance() {
        return instance;
    }
}
