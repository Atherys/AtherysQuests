package com.atherys.quests.facade;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.service.DialogService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;

@Singleton
public class DialogFacade {

    @Inject
    private DialogService service;

    public void onPlayerInteractWithEntity(Player player, Entity entity) {
        if (AtherysQuests.getInstance().getDialogAttachmentService().isAttaching(player)) {
            AtherysQuests.getInstance().getDialogAttachmentService().applyAttachment(player, entity);
            AtherysQuests.getInstance().getQuestMessagingService().info(player, "Dialog set.");

        } else if (AtherysQuests.getInstance().getDialogAttachmentService().isRemoving(player)) {
            AtherysQuests.getInstance().getDialogService().removeDialog(entity);
            AtherysQuests.getInstance().getQuestMessagingService().info(player, "Dialog removed from entity.");
            AtherysQuests.getInstance().getDialogAttachmentService().endRemoval(player);

        } else {
            AtherysQuests.getInstance().getDialogService().startDialog(player, entity);
        }
    }

}
