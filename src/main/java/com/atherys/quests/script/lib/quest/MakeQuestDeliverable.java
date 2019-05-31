package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quest.modifiers.Deliverable;
import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.script.api.util.QuadFunction;
import org.spongepowered.api.text.Text;

import java.util.UUID;

/**
 * @jsfunc
 */
public class MakeQuestDeliverable implements QuadFunction<Quest, UUID, Text, DialogNode, Boolean> {
    /**
     * Makes a quest deliverable, meaning it must be turned in to an NPC.
     * @param quest The quest.
     * @param target The UUID of the entity to turn in the quest to.
     * @param targetName How the NPC is referenced in the quest.
     * @param node The dialog to complete the quest. This will be attached as a response in the root
     *             node of the NPC. If the NPC does not have a dialog to begin with, this will not work.
     */
    @Override
    public Boolean apply(Quest quest, UUID target, Text targetName, DialogNode node) {
        quest.makeDeliverable(new Deliverable(target, targetName, node));
        return true;
    }
}
