package com.atherys.quests.script.lib.dialog;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogNodeBuilder;
import com.atherys.quests.util.HexaFunction;
import org.spongepowered.api.text.Text;

public class CreateDialogNode implements HexaFunction<Integer, Requirement[], Text, Quest, Text[], DialogNode[], DialogNode> {
    @Override
    public DialogNode apply(Integer id, Requirement[] requirements, Text player, Quest quest, Text[] npc, DialogNode[] responses) {
        DialogNodeBuilder builder = DialogNode.builder(id);

        if ( requirements != null ) builder.requirements(requirements);

        if ( quest != null ) builder.quest(quest);

        if ( responses != null ) builder.responses(responses);

        return builder.player(player).npc(npc).build();
    }
}
