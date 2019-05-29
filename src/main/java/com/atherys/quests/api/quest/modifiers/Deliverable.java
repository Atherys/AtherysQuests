package com.atherys.quests.api.quest.modifiers;

import com.atherys.quests.dialog.tree.DialogNode;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;

import java.util.UUID;

public class Deliverable {
    @Expose
    private UUID target;

    @Expose
    private Text targetName;
    private DialogNode node;

    public Deliverable(UUID target, Text targetName, DialogNode node) {
        this.target = target;
        this.targetName = targetName;
        this.node = node;
    }

    public UUID getTarget() {
        return target;
    }

    public Text getTargetName() {
        return targetName;
    }

    public DialogNode getNode() {
        return node;
    }
}
