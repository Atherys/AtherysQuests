package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.quest.DeliverableSimpleQuest;
import com.atherys.quests.quest.SimpleQuest;
import com.atherys.script.api.util.QuadFunction;
import org.spongepowered.api.text.Text;

import java.util.UUID;

/**
 * @jsfunc
 */
public class CreateDeliverableSimpleQuest implements QuadFunction<SimpleQuest, UUID, DialogNode, Text, DeliverableSimpleQuest> {
    /**
     * Creates a deliverable version of a simple quest, using a simple quest.
     * @param originalQuest The original quest to use.
     * @param entity The UUID of the entity to deliver the quest to.
     * @param node The dialog node which will be used to deliver the quest. A dialog tree will be generated and placed on the entity if it does not exist.
     * @param name How to refer to the NPC in the quest.
     * @return A DeliverableSimpleQuest
     */
    @Override
    public DeliverableSimpleQuest apply(SimpleQuest originalQuest, UUID entity, DialogNode node, Text name) {
        DeliverableSimpleQuest quest = new DeliverableSimpleQuest(originalQuest);
        quest.setEntity(entity, name);
        quest.applyNode(node);
        return quest;
    }
}
