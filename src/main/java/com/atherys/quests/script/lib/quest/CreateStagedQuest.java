package com.atherys.quests.script.lib.quest;

import com.atherys.quests.quest.StagedQuest;
import com.atherys.script.api.function.ScriptQuadFunction;
import org.spongepowered.api.text.Text;

/**
 * @jsfunc
 */
public class CreateStagedQuest implements ScriptQuadFunction<String, Text, Text, Integer, StagedQuest> {
    @Override
    public StagedQuest call(String id, Text name, Text description, Integer version) {
        return new StagedQuest(id, name, description, version);
    }
}
