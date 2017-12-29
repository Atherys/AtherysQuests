package com.atherys.quests.quest.objective;

import com.atherys.quests.events.DialogProceedEvent;
import com.atherys.quests.quester.Quester;

public class DialogObjective extends AbstractObjective<DialogProceedEvent> {

    private String requiredDialogTree;
    private int requiredDialogNode;

    private boolean complete = false;

    public DialogObjective ( String treeId, int node ) {
        super(DialogProceedEvent.class);
        this.requiredDialogTree = treeId;
        this.requiredDialogNode = node;
    }

    @Override
    protected void onNotify ( DialogProceedEvent event, Quester quester ) {
        if ( event.getDialog().getTreeId().equals(requiredDialogTree) && event.getDialog().getLastNode().getId() == requiredDialogNode ) {
            this.complete = true;
        }
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public Objective copy() {
        return new DialogObjective( this.requiredDialogTree, this.requiredDialogNode );
    }
}
