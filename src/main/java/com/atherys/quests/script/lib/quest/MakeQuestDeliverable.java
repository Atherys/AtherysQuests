package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quest.modifiers.DeliveryComponent;
import com.atherys.script.api.function.ScriptBiFunction;
import org.spongepowered.api.text.Text;

/**
 * @jsfunc
 */
public class MakeQuestDeliverable implements ScriptBiFunction<Quest, Text, Boolean> {
    /**
     * Makes a quest deliverable, meaning it must be turned in to an NPC.
     * @param quest The quest.
     * @param message A message to tell the player how to turn in the quest.
     */
    @Override
    public Boolean call(Quest quest, Text message) {
        quest.makeDeliverable(new DeliveryComponent(message));
        return true;
    }
}
