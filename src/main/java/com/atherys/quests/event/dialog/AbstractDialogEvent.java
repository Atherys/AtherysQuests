package com.atherys.quests.event.dialog;

import com.atherys.quests.dialog.Dialog;
import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.util.AbstractEvent;

public abstract class AbstractDialogEvent extends AbstractEvent {

    private DialogNode node;
    private Dialog dialog;

    public AbstractDialogEvent(DialogNode node, Dialog dialog) {
        super(dialog.getCachedPlayer(), node, dialog);
        this.node = node;
        this.dialog = dialog;
    }

    public DialogNode getNode() {
        return node;
    }

    public Dialog getDialog() {
        return dialog;
    }
}
