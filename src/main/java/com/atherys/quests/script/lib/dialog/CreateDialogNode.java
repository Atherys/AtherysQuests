package com.atherys.quests.script.lib.dialog;

import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogNodeBuilder;
import com.atherys.script.api.function.ScriptFunction;

/**
 * @jsfunc
 */
public class CreateDialogNode implements ScriptFunction<Integer, DialogNodeBuilder> {

    /**
     * Returns a `DialogNodeBuilder`, which is used to create a `DialogNode`. See
     * [Writing a Dialog](https://atherys.com/docs/scripting/quests/Writing-a-Dialog.html) for how to use it.
     *
     * @param id The numerical id of the node ( must be unique within the Dialog Tree )
     * @return A DialogNodeBuilder object
     * @jsname dialogNode
     */
    @Override
    public DialogNodeBuilder call(Integer id) {
        return DialogNode.builder(id);
    }
}
