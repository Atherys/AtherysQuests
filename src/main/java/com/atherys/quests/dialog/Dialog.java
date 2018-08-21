package com.atherys.quests.dialog;

import com.atherys.core.views.Viewable;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogTree;
import com.atherys.quests.event.dialog.DialogEndEvent;
import com.atherys.quests.event.dialog.DialogProceedEvent;
import com.atherys.quests.event.dialog.DialogStartEvent;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.views.DialogView;
import com.atherys.quests.views.TakeQuestView;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;

import javax.annotation.Nullable;
import java.util.Optional;

public class Dialog implements Viewable<DialogView> {

    private String treeId;

    private Quester quester;
    private Entity npc;

    private DialogNode lastNode;

    private Player cachedPlayer;

    private Dialog(Quester player, Entity entity, DialogTree tree) {
        this.treeId = tree.getId();
        this.quester = player;
        this.npc = entity;
        this.lastNode = tree.getRoot();
    }

    public static Optional<Dialog> between(Player player, Entity entity, DialogTree dialogTree) {
        Quester quester = AtherysQuests.getQuesterManager().getQuester(player);

        Dialog dialog = new Dialog(quester, entity, dialogTree);
        dialog.proceed(player, dialog.getLastNode());
        return Optional.of(dialog);
    }

    public DialogNode getLastNode() {
        return lastNode;
    }

    public void setLastNode(DialogNode lastNode) {
        this.lastNode = lastNode;
    }

    public void proceed(Player player, DialogNode node) {

        // update the cached player
        this.cachedPlayer = player;

        if ( node.getId() == 0 ) {
            Sponge.getEventManager().post(new DialogStartEvent(node, this));
        } else if ( node.getResponses().isEmpty() ) {
            Sponge.getEventManager().post(new DialogEndEvent(node, this));
        } else {
            Sponge.getEventManager().post(new DialogProceedEvent(node, this));
        }

        // If the node provided is not the current node or a child of the current node, return.
        if (this.lastNode == node || lastNode.getResponses().contains(node)) {

            if (!node.meetsRequirements(quester)) {
                DialogMsg.error(player, "You do not meet the requirements for this response.");
                return;
            }

            this.lastNode = node;

            new DialogView(this).show(player);

            node.getQuest().ifPresent(quest -> new TakeQuestView(quest).show(player));

            if (node.getResponses().isEmpty()) {
                AtherysQuests.getDialogService().removePlayerDialog(player);
            }
        }
    }

    public Entity getNPC() {
        return npc;
    }

    public Optional<? extends User> getPlayer() {
        return quester.getUser();
    }

    @Nullable
    public Player getCachedPlayer() {
        return cachedPlayer;
    }

    public String getTreeId() {
        return treeId;
    }

    @Override
    public DialogView createView() {
        return new DialogView(this);
    }
}
