package com.atherys.quests.facade;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.exception.QuestCommandException;
import com.atherys.quests.dialog.tree.DialogTree;
import com.atherys.quests.service.DialogAttachmentService;
import com.atherys.quests.service.DialogService;
import com.atherys.quests.service.QuestMessagingService;
import com.atherys.quests.util.EntityUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.spongepowered.api.text.format.TextColors.DARK_GRAY;
import static org.spongepowered.api.text.format.TextColors.GOLD;

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
        Optional<Entity> entity = EntityUtils.getNonPlayerFacingEntity(player, MAX_ENTITY_RADIUS);
        if (entity.isPresent()) {
            Optional<DialogTree> tree = dialogService.getDialog(entity.get());

            if (tree.isPresent()) {
                questMsg.info(player, Text.of("Dialog ID: ", tree.get().getId()));
                return;
            }
        }
        throw new QuestCommandException(Text.of("No attached dialog found."));
    }

    public void getFacingEntityUuid(Player player) {
        EntityUtils.getNonPlayerFacingEntity(player, MAX_ENTITY_RADIUS).ifPresent(entity -> {
            questMsg.info(player, Text.of("Entity UUID: ", entity.getUniqueId()));
        });
    }

    public void startRemovingDialogFromFacingEntity(Player player) {
        dialogAttachmentService.startRemoval(player);
        questMsg.info(player, "Right click an entity to remove their dialog.");
    }

    public void listDialogs(CommandSource source) {
        Task.builder()
                .execute(() -> {
                    PaginationList.builder()
                            .title(Text.of(GOLD, "Dialogs"))
                            .padding(Text.of(DARK_GRAY, "="))
                            .contents(dialogService.getAllDialogs().stream()
                                    .map(tree -> Text.of(tree.getId()))
                                    .collect(Collectors.toList()))
                            .build().sendTo(source);
                })
                .async()
                .submit(AtherysQuests.getInstance());
    }

    public void removePlayerFromDialog(Player player) {
        if (dialogService.hasPlayerDialog(player)) {
            dialogService.removePlayerDialog(player);
            questMsg.info(player, "Dialog quit!");
        } else {
            questMsg.error(player, "You are not in a dialog.");
        }
    }
}
