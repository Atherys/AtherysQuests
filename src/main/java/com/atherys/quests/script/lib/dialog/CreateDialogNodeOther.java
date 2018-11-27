package com.atherys.quests.script.lib.dialog;

import com.atherys.quests.dialog.tree.DialogNode;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.function.BiFunction;

public class CreateDialogNodeOther implements BiFunction<Integer, List<Text>, DialogNode> {
    @Override
    public DialogNode apply(Integer id, List<Text> npc) {
        return DialogNode.builder(id).npc(npc.get(0)).build();
    }
}
