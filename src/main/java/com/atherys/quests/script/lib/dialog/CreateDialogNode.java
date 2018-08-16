package com.atherys.quests.script.lib.dialog;

import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.script.api.util.QuadFunction;
import org.spongepowered.api.text.Text;

public class CreateDialogNode implements QuadFunction<Integer, Text, Text[], DialogNode[], DialogNode> {
    @Override
    public DialogNode apply(Integer id, Text player, Text[] npc, DialogNode[] responses) {
        return DialogNode.builder(id).player(player).npc(npc).responses(responses).build();
    }
}
