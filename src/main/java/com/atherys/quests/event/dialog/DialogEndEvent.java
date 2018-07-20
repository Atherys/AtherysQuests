package com.atherys.quests.event.dialog;

import com.atherys.quests.dialog.Dialog;
import com.atherys.quests.dialog.tree.DialogNode;

public class DialogEndEvent extends AbstractDialogEvent {
    public DialogEndEvent(DialogNode node, Dialog dialog) {
        super(node, dialog);
    }
}
