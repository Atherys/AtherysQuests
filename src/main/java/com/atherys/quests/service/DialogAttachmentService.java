package com.atherys.quests.service;

import com.atherys.core.interaction.AbstractAttachmentService;
import com.atherys.quests.AtherysQuests;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;

public class DialogAttachmentService extends AbstractAttachmentService<String> {

    private static DialogAttachmentService instance = new DialogAttachmentService();

    private DialogAttachmentService() {
        super();
    }

    public static DialogAttachmentService getInstance() {
        return instance;
    }

    public void applyAttachment(Player player, Entity entity) {
        getAttachment(player).ifPresent(id -> {
            AtherysQuests.getDialogService().getDialogFromId(id).ifPresent(dialogTree -> {
                AtherysQuests.getDialogService().setDialog(entity, dialogTree);
            });
        });
    }

    @Override
    public void applyAttachment(Player player) {

    }
}
