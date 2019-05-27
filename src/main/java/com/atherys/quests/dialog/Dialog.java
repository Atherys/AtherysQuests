package com.atherys.quests.dialog;

import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogTree;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nullable;

public class Dialog {

    private String treeId;

    private Quester quester;

    private Entity npc;

    private DialogNode lastNode;

    private Player cachedPlayer;

    public Dialog(Quester player, Entity entity, DialogTree tree) {
        this.treeId = tree.getId();
        this.quester = player;
        this.npc = entity;
        this.lastNode = tree.getRoot();
    }

    public DialogNode getLastNode() {
        return lastNode;
    }

    public void setLastNode(DialogNode lastNode) {
        this.lastNode = lastNode;
    }

    public Entity getNPC() {
        return npc;
    }

    @Nullable
    public Player getCachedPlayer() {
        return cachedPlayer;
    }

    public String getTreeId() {
        return treeId;
    }

    public Quester getQuester() {
        return quester;
    }

    public void updatePlayer(Player player) {
        this.cachedPlayer = player;
    }
}
