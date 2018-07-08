package com.atherys.quests.api.script;

import java.util.Optional;

public interface ScriptService {

    Optional<QuestScript> getQuestScriptById(String scriptId);

    Optional<DialogScript> getDialogScriptById(String scriptId);

}
