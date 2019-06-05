package com.atherys.quests.api.quest.modifiers;

import com.atherys.quests.api.base.Prototype;
import com.atherys.quests.dialog.tree.DialogNode;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;

import java.util.UUID;

public class DeliveryComponent implements Prototype<DeliveryComponent> {
    @Expose
    private UUID target;

    @Expose
    private Text targetName;
    private DialogNode node;

    public DeliveryComponent(UUID target, Text targetName, DialogNode node) {
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

    @Override
    public DeliveryComponent copy() {
        return new DeliveryComponent(target, targetName, node);
    }
}
