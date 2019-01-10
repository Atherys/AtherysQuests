package com.atherys.quests.api.script;

import com.atherys.quests.script.SimpleQuestScriptService;
import com.atherys.script.api.ScriptService;
import com.google.inject.ImplementedBy;

@ImplementedBy(SimpleQuestScriptService.class)
public interface QuestScriptService extends ScriptService<QuestScript> {
}
