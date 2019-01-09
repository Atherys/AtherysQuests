package com.atherys.quests.facade;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.QuestKeys;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quest.QuestLocationType;
import com.atherys.quests.service.QuestAttachmentService;
import com.atherys.quests.service.QuestLocationService;
import com.atherys.quests.service.QuestMessagingService;
import com.atherys.quests.service.QuestService;
import com.atherys.quests.views.QuestFromItemView;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
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

    public void onBlockInteract(Location<World> blockLocation, Player player) {

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
            Optional<Quest> quest = AtherysQuests.getInstance().getQuestService().getQuest(id);

            quest.ifPresent(quest1 -> new QuestFromItemView(quest1).show(player));

            event.setCancelled(true);
        });
    }

}
