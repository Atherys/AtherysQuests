package com.atherys.quests.script.lib.dialog;

import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogTree;

import java.util.function.BiFunction;

public class CreateDialogTree implements BiFunction<String, DialogNode, DialogTree> {
    @Override
    public DialogTree apply(String id, DialogNode root) {
        return DialogTree.builder(id).root(root).build();
    }
}
