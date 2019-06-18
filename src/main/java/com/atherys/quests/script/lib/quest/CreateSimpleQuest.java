package com.atherys.quests.script.lib.quest;

import com.atherys.quests.quest.SimpleQuest;
import com.atherys.script.api.function.ScriptQuadFunction;
import org.spongepowered.api.text.Text;

/**
 * @jsfunc
 */
public class CreateSimpleQuest implements ScriptQuadFunction<String, Text, Text, Integer, SimpleQuest> {

    @Override
    public SimpleQuest call(String id, Text name, Text description, Integer version) {
        return new SimpleQuest(id, name, description, version);
    }

}
