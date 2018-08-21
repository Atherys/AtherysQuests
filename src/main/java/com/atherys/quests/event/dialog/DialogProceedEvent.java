package com.atherys.quests.event.dialog;

import com.atherys.quests.dialog.Dialog;
import com.atherys.quests.dialog.tree.DialogNode;

public class DialogProceedEvent extends AbstractDialogEvent {
    public DialogProceedEvent(DialogNode node, Dialog dialog) {
        super(node, dialog);
    }
}
