package com.atherys.quests.script.lib.npc;

import com.atherys.quests.AtherysQuests;

import java.util.UUID;
import java.util.function.Function;

/**
 * @jsfunc
 */
public class GetNpc implements Function<String, UUID> {
    /**
     * Gets the UUID for the NPC with the name.
     */
    @Override
    public UUID apply(String npcName) {
        return AtherysQuests.getConfig().NPCS.get(npcName);
    }
}
