package com.atherys.quests.facade;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.exception.QuestCommandException;
import com.atherys.quests.dialog.tree.DialogTree;
import com.atherys.quests.service.DialogAttachmentService;
import com.atherys.quests.service.DialogService;
import com.atherys.quests.service.QuestMessagingService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.extent.EntityUniverse;

import java.util.Optional;

@Singleton
public class DialogFacade {

    public static final int MAX_ENTITY_RADIUS = 5;

    @Inject
    DialogService dialogService;

    @Inject
    DialogAttachmentService dialogAttachmentService;

    @Inject
    QuestMessagingService questMsg;

    DialogFacade() {
    }

    public void onPlayerInteractWithEntity(Player player, Entity entity) {
        if (dialogAttachmentService.isAttaching(player)) {

            dialogAttachmentService.applyAttachment(player, entity);

            questMsg.info(player, "Dialog set.");
        } else if (dialogAttachmentService.isRemoving(player)) {
            dialogService.removeDialog(entity);
            dialogAttachmentService.endRemoval(player);

            questMsg.info(player, "Dialog removed from entity.");
        } else {
            dialogService.startDialog(player, entity);
        }
    }

    public void beginDialogAttachment(Player player, String dialogId) throws CommandException {
        if (dialogService.getDialogFromId(dialogId).isPresent()) {
            dialogAttachmentService.startAttachment(player, dialogId);
            questMsg.info(player, "Right click an entity to attach the dialog.");
        } else {
            throw new QuestCommandException(Text.of("Dialog with ID ", dialogId, " does not exist."));
        }
    }

    public void getFacingEntityDialogId(Player player) throws CommandException {
        for (EntityUniverse.EntityHit entityHit : player.getWorld().getIntersectingEntities(player, MAX_ENTITY_RADIUS)) {

            Entity next = entityHit.getEntity();

            if (next instanceof Player) {
                continue;
            }

            Optional<DialogTree> tree = dialogService.getDialog(entityHit.getEntity());

            if ( tree.isPresent() ) {
                questMsg.info(player, Text.of("Dialog ID: ", tree.get().getId()));
                return;
            }
        }

        throw new QuestCommandException(Text.of("No attached dialog found."));
    }

    public void getFacingEntityUuid(Player player) throws CommandException {
        for (EntityUniverse.EntityHit entityHit : player.getWorld().getIntersectingEntities(player, MAX_ENTITY_RADIUS)) {
            Entity next = entityHit.getEntity();

            if (next instanceof Player) {
                continue;
            }

            questMsg.info(player, Text.of("Entity UUID: ", next.getUniqueId()));
            return;
        }
    }

    public void startRemovingDialogFromFacingEntity(Player player) {
        dialogAttachmentService.startRemoval(player);
        questMsg.info(player, "Right click an entity to remove their dialog.");
    }
}
