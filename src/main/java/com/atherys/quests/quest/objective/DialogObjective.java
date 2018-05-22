package com.atherys.quests.quest.objective;

import com.atherys.quests.api.objective.AbstractObjective;
import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.events.DialogProceedEvent;
import com.atherys.quests.quester.Quester;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import javax.annotation.Nullable;

/**
 * An {@link Objective} for interacting with Dialogs.
 */
public class DialogObjective extends AbstractObjective<DialogProceedEvent> {

    @Expose
    private String requiredDialogTree;
    @Expose
    private int requiredDialogNode;
    @Expose
    private Text description = Text.EMPTY;

    @Expose
    private boolean complete = false;

    private DialogObjective() {
        super(DialogProceedEvent.class);
    }

    DialogObjective(String treeId, int node, @Nullable Text description) {
        this();
        this.requiredDialogTree = treeId;
        this.requiredDialogNode = node;
        if (description != null) {
            this.description = description;
        }
    }

    @Override
    protected void onNotify(DialogProceedEvent event, Quester quester) {
        if (event.getDialog().getTreeId().equals(requiredDialogTree) && event.getDialog().getLastNode().getId() == requiredDialogNode) {
            this.complete = true;
        }
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public Objective copy() {
        return new DialogObjective(this.requiredDialogTree, this.requiredDialogNode, this.description);
    }

    @Override
    public Text toText() {
        return Text.builder()
                .append(Text.of("Speak to NPC."))
                .onHover(TextActions.showText(description))
                .build();
    }

    public Text getDescription() {
        return description;
    }
}
