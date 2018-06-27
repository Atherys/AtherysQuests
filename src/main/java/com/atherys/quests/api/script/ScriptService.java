package com.atherys.quests.api.script;

import com.atherys.quests.api.quest.Quest;

import java.util.Optional;

public interface ScriptService<T extends Script> {

    Optional<T> forQuest(Quest quest);

}
