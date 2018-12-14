package com.atherys.quests.script.lib.dialog;

import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogNodeBuilder;

import java.util.function.Function;

/**
 * @jsfunc
 */
public class CreateDialogNode implements Function<Integer, DialogNodeBuilder> {

    /**
     * Returns a `DialogNodeBuilder`, which is used to create a `DialogNode`. See
     * [Writing a Dialog](https://atherys.com/docs/scripting/quests/Writing-a-Dialog.html) for how to use it.
     *
     * @jsname dialogNode
     *
     * @param id           The numerical id of the node ( must be unique within the Dialog Tree )
     * @return A DialogNodeBuilder object
     */
    @Override
    public DialogNodeBuilder apply(Integer id) {
        return DialogNode.builder(id);
    }
}
