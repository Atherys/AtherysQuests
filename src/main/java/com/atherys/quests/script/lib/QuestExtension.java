package com.atherys.quests.script.lib;

import com.atherys.quests.event.dialog.DialogEndEvent;
import com.atherys.quests.event.dialog.DialogProceedEvent;
import com.atherys.quests.event.dialog.DialogRegistrationEvent;
import com.atherys.quests.event.dialog.DialogStartEvent;
import com.atherys.quests.event.quest.*;
import com.atherys.quests.script.lib.quest.AddQuestObjectives;
import com.atherys.quests.script.lib.quest.CreateSimpleQuest;
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
        scriptLibrary.put("onDialogStart", new EventHandlerFunction<>(DialogStartEvent.class));
        scriptLibrary.put("onDialogProgress", new EventHandlerFunction<>(DialogProceedEvent.class));
        scriptLibrary.put("onDialogComplete", new EventHandlerFunction<>(DialogEndEvent.class));

        scriptLibrary.put("onQuestRegistration", new EventHandlerFunction<>(QuestRegistrationEvent.class));
        scriptLibrary.put("onQuestStart", new EventHandlerFunction<>(QuestStartedEvent.class));
        scriptLibrary.put("onSimpleQuestProgress", new EventHandlerFunction<>(SimpleQuestProgressEvent.class));
        scriptLibrary.put("onStagedQuestProgress", new EventHandlerFunction<>(StagedQuestProgressEvent.class));
        scriptLibrary.put("onQuestComplete", new EventHandlerFunction<>(QuestCompletedEvent.class));
        scriptLibrary.put("onQuestTurnIn", new EventHandlerFunction<>(QuestTurnedInEvent.class));

        scriptLibrary.put("createSimpleQuest", new CreateSimpleQuest());
        scriptLibrary.put("addQuestObjectives", new AddQuestObjectives());
    }

    public static QuestExtension getInstance() {
        return instance;
    }
}
