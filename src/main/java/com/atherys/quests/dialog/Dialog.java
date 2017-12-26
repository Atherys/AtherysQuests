package com.atherys.quests.dialog;

import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogTree;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class Dialog {

    private Player playerObject;
    private Entity npc;

    private DialogNode lastNode;

    private Dialog ( Player player, Entity entity, DialogTree tree ) {
        this.playerObject = player;
        this.npc = entity;
        this.lastNode = tree.getRoot();
    }

    public static Dialog between ( Player player, Entity entity, DialogTree dialogTree ) {
        Dialog dialog = new Dialog ( player, entity, dialogTree );
        dialog.proceed( dialog.getLastNode() );
        return dialog;
    }

    public DialogNode getLastNode() {
        return lastNode;
    }

    public void setLastNode ( DialogNode lastNode ) {
        this.lastNode = lastNode;
    }

    private void proceed( DialogNode node ) {
        // TODO: Get player from UserUtils in case player has died, reconnected, etc. etc.

        // If the node provided is not the current node or a child of the current node, return.
        if ( !( this.lastNode == node || node.getResponses().contains(node) ) ) return;

        if ( !node.meetsRequirements( playerObject ) ) {
            DialogMsg.error( playerObject, "You do not meet the requirements for this response." );
            return;
        }

        this.lastNode = node;

        playerObject.sendMessage( DialogMsg.DIALOG_START_DECORATION );

        if ( node.getPlayerText() != null ) DialogMsg.self( playerObject, node.getPlayerText() );
        if ( node.getNPCText() != null ) DialogMsg.npc ( playerObject, npc, node.getNPCText() );

        if ( node.getResponses().size() >= 1 ) {
            playerObject.sendMessage(DialogMsg.DIALOG_REPLIES_DECORATION);

            int i = 1;
            for ( DialogNode response : node.getResponses() ) {
                Text.Builder nextMessage = Text.builder()
                        .append(Text.of(TextColors.DARK_AQUA, "[", TextColors.WHITE, TextStyles.BOLD, i, TextStyles.RESET, TextColors.DARK_AQUA, "]"))
                        .append(Text.of(TextColors.AQUA, TextStyles.BOLD, "You", TextStyles.RESET, TextColors.RESET, ": ", response.getPlayerText()))
                        .onClick(TextActions.executeCallback(commandSource -> this.proceed(response)))
                        .onHover(TextActions.showText(Text.of("Say ", TextStyles.ITALIC, response.getPlayerText())));

                response.getQuest().ifPresent(quest -> {
                    nextMessage.append(Text.of(TextColors.DARK_GREEN, TextStyles.BOLD, " { Starts Quest: ", TextColors.GREEN, TextStyles.RESET, quest.getName(), TextStyles.BOLD, TextColors.DARK_GREEN, " }"));
                });

                playerObject.sendMessage(nextMessage.build());
                i++;
            }
        } else playerObject.sendMessage( DialogMsg.DIALOG_END_DECORATION );
    }
}
