package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.quest.DeliverableQuest;
import com.atherys.script.api.util.QuadFunction;
import org.spongepowered.api.text.Text;

import java.util.UUID;

/**
 * @jsfunc
 */
public class CreateDeliverableQuest implements QuadFunction<Quest, DialogNode, UUID, Text, DeliverableQuest> {
    /**
     * Creates a deliverable version of a simple quest, using a simple quest.
     * @param originalQuest The original quest to use.
     * @param node The node that will turn the quest in. The NPC will require a DialogTree.
     * @param entity The UUID of the entity to deliver the quest to.
     * @param name How to refer to the NPC in the quest.
     * @return A DeliverableSimpleQuest
     */
    @Override
    public DeliverableQuest apply(Quest originalQuest, DialogNode node, UUID entity, Text name) {
         return new DeliverableQuest<>(originalQuest, node, entity, name);
    }
}
