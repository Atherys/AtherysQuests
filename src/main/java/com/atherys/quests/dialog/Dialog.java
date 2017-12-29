package com.atherys.quests.dialog;

import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogTree;
import com.atherys.quests.events.DialogProceedEvent;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.managers.QuesterManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;

import java.util.Optional;

public class Dialog {

    private String treeId;

    private Quester quester;
    private Entity npc;

    private DialogNode lastNode;

    private Player cachedPlayer;

    private Dialog ( Quester player, Entity entity, DialogTree tree ) {
        this.treeId = tree.getId();
        this.quester = player;
        this.npc = entity;
        this.lastNode = tree.getRoot();
    }

    public static Optional<Dialog> between ( Player player, Entity entity, DialogTree dialogTree ) {
        Optional<Quester> quester = QuesterManager.getInstance().getQuester( player );
        if ( !quester.isPresent() ) return Optional.empty();

        Dialog dialog = new Dialog ( quester.get(), entity, dialogTree );
        dialog.proceed( player, dialog.getLastNode() );
        return Optional.of( dialog );
    }

    public DialogNode getLastNode() {
        return lastNode;
    }

    public void setLastNode ( DialogNode lastNode ) {
        this.lastNode = lastNode;
    }

    protected void proceed( Player player, DialogNode node ) {

        this.cachedPlayer = player;

        DialogProceedEvent event = new DialogProceedEvent( this );
        Sponge.getEventManager().post( event );

        // If the node provided is not the current node or a child of the current node, return.
        if ( !( this.lastNode == node || node.getResponses().contains( node ) ) ) return;


        if ( !node.meetsRequirements( quester ) ) {
            DialogMsg.error( player, "You do not meet the requirements for this response." );
            return;
        }

        this.lastNode = node;

        new DialogView( this ).showChat( player );
    }

    public Entity getNPC() {
        return npc;
    }

    public Optional<User> getPlayer() {
        return quester.getUser();
    }

    public Player getCachedPlayer() {
        return cachedPlayer;
    }

    public String getTreeId() {
        return treeId;
    }
}
