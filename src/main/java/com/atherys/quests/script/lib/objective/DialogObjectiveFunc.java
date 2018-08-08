package com.atherys.quests.script.lib.objective;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.quest.objective.Objectives;
import com.atherys.script.api.util.TriFunction;
import org.spongepowered.api.text.Text;

public class DialogObjectiveFunc implements TriFunction<String, Integer, Text, Objective> {
    @Override
    public Objective apply(String treeId, Integer dialogNode, Text description) {
        return Objectives.dialog(treeId, dialogNode, description);
    }
}
