package com.atherys.quests.api.script;

import com.atherys.quests.script.SimpleDialogScriptService;
import com.atherys.script.api.ScriptService;
import com.google.inject.ImplementedBy;

@ImplementedBy(SimpleDialogScriptService.class)
public interface DialogScriptService extends ScriptService<DialogScript> {
}
