package com.atherys.quests.script.lib.dialog;

import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogTree;

import java.util.function.BiFunction;

/**
 * @jsfunc
 */
public class CreateDialogTree implements BiFunction<String, DialogNode, DialogTree> {
    /**
     * Creates a dialog tree.
     *
     * @param id   A unique string ID.
     * @param root The starting dialog node.
     * @jsname dialogTree
     */
    @Override
    public DialogTree apply(String id, DialogNode root) {
        return DialogTree.builder(id).root(root).build();
    }
}
