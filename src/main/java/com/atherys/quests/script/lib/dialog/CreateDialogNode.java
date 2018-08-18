package com.atherys.quests.script.lib.dialog;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogNodeBuilder;
import com.atherys.quests.util.HexaFunction;
import org.spongepowered.api.text.Text;

import java.util.List;

public class CreateDialogNode implements HexaFunction<Integer, List<Requirement>, Text, List<Text>, Quest, List<DialogNode>, DialogNode> {

    @Override
    public DialogNode apply(Integer id, List<Requirement> requirements, Text player, List<Text> npc, Quest quest, List<DialogNode> responses) {
        DialogNodeBuilder builder = DialogNode.builder(id);

        if ( requirements != null && requirements.size() != 0 ) builder.requirements((Requirement[]) requirements.toArray());

        if ( quest != null ) builder.quest(quest);

        if ( responses != null && responses.size() != 0 ) builder.responses((DialogNode[]) responses.toArray());

        return builder.player(player).npc((Text[]) npc.toArray()).build();
    }
}
