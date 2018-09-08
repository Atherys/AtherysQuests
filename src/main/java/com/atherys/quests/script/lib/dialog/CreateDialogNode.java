package com.atherys.quests.script.lib.dialog;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.dialog.tree.DialogNode;
import com.google.gson.JsonSyntaxException;

import java.util.function.Function;

public class CreateDialogNode implements Function<String, DialogNode> {

//    @Override
//    public DialogNode apply(Integer id, Requirement[] requirements, Text player, Text[] npc, Quest completedQuest, DialogNode[] responses) {
//        DialogNodeBuilder builder = DialogNode.builder(id);
//
//        if (requirements != null) builder.requirements(requirements);
//
//        if (completedQuest != null) builder.completedQuest(completedQuest);
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
        try {
            return AtherysQuests.getRegistry().getGson().fromJson(serialized, DialogNode.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
