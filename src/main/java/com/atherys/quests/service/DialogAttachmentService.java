package com.atherys.quests.service;

import com.atherys.core.interaction.AbstractAttachmentService;
import com.atherys.quests.AtherysQuests;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;

@Singleton
public class DialogAttachmentService extends AbstractAttachmentService<String> {

    @Inject
    DialogService dialogService;

    DialogAttachmentService() {
        super();
    }

    public void applyAttachment(Player player, Entity entity) {
        getAttachment(player).ifPresent(id -> {
            dialogService.getDialogFromId(id).ifPresent(dialogTree -> dialogService.setDialog(entity, dialogTree));
        });
        endAttachment(player);
    }

    @Override
    public void applyAttachment(Player player) {

    }
}
