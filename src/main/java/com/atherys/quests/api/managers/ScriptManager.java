package com.atherys.quests.api.managers;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quest.Script;

import java.util.Optional;

public interface ScriptManager<T extends Script> {

    Optional<T> forQuest ( Quest quest );

}
