package com.atherys.quests.facade;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.service.DialogAttachmentService;
import com.atherys.quests.service.DialogService;
import com.atherys.quests.service.QuestMessagingService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;

@Singleton
public class DialogFacade {

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

}
