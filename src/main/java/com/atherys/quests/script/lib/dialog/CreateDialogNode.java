package com.atherys.quests.script.lib.dialog;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.dialog.tree.DialogNode;

import java.util.function.Function;

public class CreateDialogNode implements Function<String, DialogNode> {

//    @Override
//    public DialogNode apply(Integer id, Requirement[] requirements, Text player, Text[] npc, Quest quest, DialogNode[] responses) {
//        DialogNodeBuilder builder = DialogNode.builder(id);
//
//        if (requirements != null) builder.requirements(requirements);
//
//        if (quest != null) builder.quest(quest);
//
//        if (responses != null) builder.responses(responses);
//
//        builder.player(player);
//        builder.npc(npc);
//
//        return builder.build();
//    }

    @Override
    public DialogNode apply(String serialized) {
        return AtherysQuests.getRegistry().getGson().fromJson(serialized, DialogNode.class);
    }
}
