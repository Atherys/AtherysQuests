package com.atherys.quests.script.lib.dialog;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.dialog.tree.DialogNode;
import com.google.gson.JsonSyntaxException;

import java.util.function.Function;

/**
 * @jsfunc
 */ 
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

    /**
     * Creates a dialog node from a string representing a serialized node. See
     * [Writing a Dialog](https://atherys.com/docs/quests/Writing-a-Dialog.html) for how to use it.
     */
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
