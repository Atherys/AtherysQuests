package com.atherys.quests.script.lib.quest;

import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.quest.DeliverableStagedQuest;
import com.atherys.quests.quest.StagedQuest;
import com.atherys.script.api.util.QuadFunction;
import org.spongepowered.api.text.Text;

import java.util.UUID;

/**
 * @jsfunc
 */
public class CreateDeliverableStagedQuest implements QuadFunction<StagedQuest, UUID, Text, DialogNode, DeliverableStagedQuest> {
    /**
    * Creates a deliverable version of a staged quest, using a staged quest.
    * @param originalQuest The original quest to use.
    * @param entity The UUID of the entity to deliver the quest to.
    * @param node The dialog node which will be used to deliver the quest. A dialog tree will be generated and placed on the entity if it does not exist.
    * @param name How to refer to the NPC in the quest.
    * @return A DeliverableStagedQuest
    */
    @Override
    public DeliverableStagedQuest apply(StagedQuest originalQuest, UUID entity, Text name, DialogNode node) {
        DeliverableStagedQuest quest = new DeliverableStagedQuest(originalQuest);
        quest.setEntity(entity, name);
        quest.applyNode(node);
        return quest;
    }
}
