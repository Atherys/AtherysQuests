package com.atherys.quests.facade;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.service.DialogService;
import com.atherys.quests.util.QuestMsg;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;

@Singleton
public class DialogFacade {

    @Inject
    private DialogService service;

    public void onPlayerInteractWithEntity(Player player, Entity entity) {
        if (AtherysQuests.getDialogAttachmentService().isAttaching(player)) {
            AtherysQuests.getDialogAttachmentService().applyAttachment(player, entity);
            QuestMsg.info(player, "Dialog set.");

        } else if (AtherysQuests.getDialogAttachmentService().isRemoving(player)) {
            AtherysQuests.getDialogService().removeDialog(entity);
            QuestMsg.info(player, "Dialog removed from entity.");
            AtherysQuests.getDialogAttachmentService().endRemoval(player);

        } else {
            AtherysQuests.getDialogService().startDialog(player, entity);
        }
    }

}
