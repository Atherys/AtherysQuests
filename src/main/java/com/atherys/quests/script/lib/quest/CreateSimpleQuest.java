package com.atherys.quests.script.lib.quest;

import com.atherys.quests.quest.SimpleQuest;
import com.atherys.script.api.util.QuadFunction;
import org.spongepowered.api.text.Text;

/**
 * @jsfunc
 */
public class CreateSimpleQuest implements QuadFunction<String, Text, Text, Integer, SimpleQuest> {

    @Override
    public SimpleQuest apply(String id, Text name, Text description, Integer version) {
        return new SimpleQuest(id, name, description, version);
    }

}
