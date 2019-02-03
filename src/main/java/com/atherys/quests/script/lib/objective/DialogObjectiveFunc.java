package com.atherys.quests.script.lib.objective;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.quest.objective.Objectives;
import com.atherys.script.api.util.TriFunction;
import org.spongepowered.api.text.Text;

/**
 * @jsfunc
 */
public class DialogObjectiveFunc implements TriFunction<String, Integer, Text, Objective> {
    /**
     * An objective that requires the player to meet a specific dialog node in a dialog tree.
     *
     * @param treeId      The dialog tree's ID.
     * @param dialogNode  The node ID within the tree.
     * @param description The objective's description.
     * @jsname dialogObjective
     */
    @Override
    public Objective apply(String treeId, Integer dialogNode, Text description) {
        return Objectives.dialog(treeId, dialogNode, description);
    }
}
