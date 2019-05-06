package com.atherys.quests.facade;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.QuestKeys;
import com.atherys.quests.api.exception.QuestCommandException;
import com.atherys.quests.api.exception.QuestCommandExceptions;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quest.QuestLocationType;
import com.atherys.quests.data.QuestData;
import com.atherys.quests.service.QuestAttachmentService;
import com.atherys.quests.service.QuestLocationService;
import com.atherys.quests.service.QuestMessagingService;
import com.atherys.quests.service.QuestService;
import com.atherys.quests.views.QuestFromItemView;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

@Singleton
public class QuestFacade {

    @Inject
    QuestService questService;

    @Inject
    QuestLocationService questLocationService;

    @Inject
    QuestAttachmentService questAttachmentService;

    @Inject
    QuestMessagingService questMsg;

    QuestFacade() {
    }

    public void onBlockInteract(Location<World> blockLocation, Player player) {

        if (blockLocation == null) {
            return;
        }

        if (questAttachmentService.isRemoving(player)) {

            questLocationService.getByLocation(player.getLocation()).ifPresent(questLocation -> {
                questLocationService.remove(questLocation);
                questMsg.info(player, "Removed quest location with ID: " + questLocation.getQuestId());
            });

        } else if (questAttachmentService.isAttaching(player)) {
            questAttachmentService.addQuestLocation(player, blockLocation);
            questAttachmentService.endAttachment(player);

            questMsg.info(player, "Quest set at location.");
        } else {
            questLocationService.getByBlock(blockLocation).ifPresent(questLocation -> {

                if (questLocation.getType() == QuestLocationType.RADIUS) return;

                questService.getQuest(questLocation.getQuestId()).ifPresent(q -> {
                    q.createView().show(player);
                });
            });
        }
    }

    public void onRightClickItem(InteractItemEvent.Secondary event, ItemStackSnapshot item, Player player) {
        Optional<String> questId = item.get(QuestKeys.QUEST);

        questId.ifPresent(id -> {
            Optional<Quest> quest = questService.getQuest(id);

            quest.ifPresent(quest1 -> new QuestFromItemView(quest1).show(player));

            event.setCancelled(true);
        });
    }

    public void attachQuestToHeldItem(Player player, String questId) throws CommandException {
        Optional<Quest> quest = questService.getQuest(questId);

        Optional<ItemStack> itemStack = player.getItemInHand(HandTypes.MAIN_HAND);

        if (!quest.isPresent()) {
            throw QuestCommandExceptions.invalidQuestId();
        }

        if (!itemStack.isPresent()) {
            throw new QuestCommandException("Item stack is not present.");
        }

        ItemStack item = itemStack.get();

        boolean isQuestItem = item.get(QuestData.class).isPresent();

        if (isQuestItem) {
            questMsg.info(player, "The current item already contains a quest with id ", item.get(QuestData.class).get(), ", will overwrite");
        }

        item.offer(new QuestData(questId));

        player.setItemInHand(HandTypes.MAIN_HAND, item);

        questMsg.info(player, "Added quest to item successfully.");
    }

    public void attachQuestToLocation(Player player, Double radius, String questId) throws CommandException {
        attachQuest(player, radius, questId, QuestLocationType.RADIUS);
    }

    public void attachQuestToBlock(Player player, Double radius, String questId) throws CommandException {
        attachQuest(player, radius, questId, QuestLocationType.BLOCK);
    }

    private void attachQuest(Player player, Double radius, String questId, QuestLocationType type) throws CommandException {
        if (questAttachmentService.isRemoving(player)) {
            throw new QuestCommandException("You are currently removing a quest.");
        }

        if (questService.getQuest(questId).isPresent()) {
            questAttachmentService.startAttachment(player, questId, radius, type);
            questMsg.info(player, "Right click on a block to set the quest.");
        } else {
            throw QuestCommandExceptions.invalidQuestId();
        }
    }

    public void cancelQuestAttachment(Player player) throws CommandException {
        questAttachmentService.endAttachment(player);
        questAttachmentService.endRemoval(player);

        questMsg.info(player, "Quest attachment cleared.");
    }

    public void detatchQuestFromHeldItem(Player player) throws CommandException {
        Optional<ItemStack> itemStack = player.getItemInHand(HandTypes.MAIN_HAND);

        itemStack.ifPresent(item -> {
            item.remove(QuestData.class);
            questMsg.info(player, "Quests removed from held item.");
        });
    }

    public void detachQuestFromLocation(Player player) throws CommandException {
        if (questAttachmentService.isAttaching(player)) {
            throw new QuestCommandException("You are currently attaching a quest.");
        } else if (questAttachmentService.isRemoving(player)) {
            throw new QuestCommandException("You are currently removing a quest.");
        } else {
            questAttachmentService.startRemoval(player);
            questMsg.info(player, "Right-click to remove a quest from the location.");
        }
    }

    public void detachQuestFromBlock() throws CommandException {
        throw QuestCommandExceptions.notImplemented();
    }

    public void offerQuest(Player player, String questId) throws CommandException {
        Optional<Quest> quest = questService.getQuest(questId);

        if (quest.isPresent()) {
            quest.get().createView().show(player);
        } else {
            throw QuestCommandExceptions.invalidQuestId();
        }
    }

    public void reloadQuests() throws CommandException {
        try {
            AtherysQuests.getInstance().getQuestScriptService().reloadScripts();
        } catch (Throwable e) {
            e.printStackTrace();

            throw new QuestCommandException(Text.builder()
                    .append(Text.of(TextColors.DARK_RED, "Reloading quest script caused following error: ", Text.NEW_LINE))
                    .append(Text.of(TextColors.RED, e.getMessage()), Text.NEW_LINE)
                    .append(Text.of(TextColors.DARK_RED, "See console for stacktrace."))
                    .build()
            );
        }
    }
}
