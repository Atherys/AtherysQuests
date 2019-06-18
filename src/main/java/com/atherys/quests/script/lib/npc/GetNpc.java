package com.atherys.quests.script.lib.npc;

import com.atherys.quests.AtherysQuests;
import com.atherys.script.api.function.ScriptFunction;

import java.util.UUID;

/**
 * @jsfunc
 */
public class GetNpc implements ScriptFunction<String, UUID> {
    /**
     * Gets the UUID for the NPC with the name.
     */
    @Override
    public UUID call(String npcName) {
        return AtherysQuests.getConfig().NPCS.get(npcName);
    }
}
