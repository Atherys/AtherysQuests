package com.atherys.quests.script.lib.quest;

import com.atherys.quests.quest.SimpleQuest;
import com.atherys.quests.quest.StagedQuest;
import com.atherys.script.api.util.QuadFunction;
import org.spongepowered.api.text.Text;

public class CreateStagedQuest implements QuadFunction<String, Text, Text, Integer, StagedQuest> {
    @Override
    public StagedQuest apply(String id, Text name, Text description, Integer version) {
        return new StagedQuest(id, name, description, version);
    }
}
