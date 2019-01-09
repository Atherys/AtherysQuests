package com.atherys.quests.listener;

import com.atherys.core.utils.Question;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.QuestKeys;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quest.QuestLocationType;
import com.atherys.quests.facade.DialogFacade;
import com.atherys.quests.facade.QuestFacade;
import com.atherys.quests.facade.QuesterFacade;
import com.atherys.quests.views.QuestFromItemView;
import com.atherys.quests.views.TakeQuestView;
import com.google.inject.Inject;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.Optional;

public class EntityListener {

    @Inject
    private QuestFacade questFacade;

    @Inject
    private DialogFacade dialogFacade;

    @Inject
    private QuesterFacade questerFacade;

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
    }

    @Listener
    public void onEntityInteract(InteractEntityEvent.Secondary.MainHand event, @Root Player player) {
        dialogFacade.onPlayerInteractWithEntity(player, event.getTargetEntity());
    }

    @Listener
    public void onPlayerMove(MoveEntityEvent e, @Root Player player) {
        if (e.getFromTransform().getLocation().getBlockPosition().equals
                (e.getToTransform().getLocation().getBlockPosition())) return;
        if (AtherysQuests.getInstance().getQuestLocationService().getByRadius(e.getFromTransform().getLocation()).isPresent()) return;

        AtherysQuests.getInstance().getQuestLocationService().getByRadius(e.getToTransform().getLocation()).ifPresent(questLocation -> {
            Quest quest = AtherysQuests.getInstance().getQuestService().getQuest(questLocation.getQuestId()).get();
            if (AtherysQuests.getInstance().getQuesterService().getQuester(player).hasQuest(quest)) return;

            Question question = Question.of(Text.of("You have found the completedQuest \"", quest.getName(), "\", would you like to take it?"))
                    .addAnswer(Question.Answer.of(Text.of(TextStyles.BOLD, TextColors.DARK_GREEN, "Yes"), quester -> {
                        if (questLocation.contains(player.getLocation())) {
                            new TakeQuestView(quest).show(quester);
                        } else {
                            AtherysQuests.getInstance().getQuestMessagingService().error(quester, "You have left the completedQuest area.");
                        }
                    }))
                    .addAnswer(Question.Answer.of(Text.of(TextStyles.BOLD, TextColors.DARK_RED, "No"), quester -> {
                        AtherysQuests.getInstance().getQuestMessagingService().error(quester, "You have declined the completedQuest \"", quest.getName(), "\".");
                    }))
                    .build();
            question.pollChat(player);
        });
    }

    @Listener
    public void onBlockInteract(InteractBlockEvent.Secondary event, @Root Player player) {
        if (!(event.getTargetBlock().getLocation().isPresent())) return;

        if (AtherysQuests.getInstance().getQuestAttachmentService().isRemoving(player)) {
            AtherysQuests.getInstance().getQuestLocationService().getByLocation(player.getLocation()).ifPresent(questLocation -> {
                AtherysQuests.getInstance().getQuestLocationService().remove(questLocation);
                AtherysQuests.getInstance().getQuestMessagingService().info(player, "Removed completedQuest location with ID: " + questLocation.getQuestId());
            });

        } else if (AtherysQuests.getInstance().getQuestAttachmentService().isAttaching(player)) {
                AtherysQuests.getInstance().getQuestAttachmentService().addQuestLocation(player, event.getTargetBlock().getLocation().get());
                AtherysQuests.getInstance().getQuestAttachmentService().endAttachment(player);
            AtherysQuests.getInstance().getQuestMessagingService().info(player, "Quest set at location.");

        } else {
            AtherysQuests.getInstance().getQuestLocationService().getByBlock(event.getTargetBlock().getLocation().get()).ifPresent(questLocation -> {
                if (questLocation.getType() == QuestLocationType.RADIUS) return;
                AtherysQuests.getInstance().getQuestService().getQuest(questLocation.getQuestId()).ifPresent(q -> {
                    q.createView().show(player);
                });
            });
        }
    }

    @Listener
    public void onRightClick(InteractItemEvent.Secondary event, @Root Player player) {
        ItemStackSnapshot item = event.getItemStack();
        Optional<String> questId = item.get(QuestKeys.QUEST);
        questId.ifPresent(id -> {
            Optional<Quest> quest = AtherysQuests.getInstance().getQuestService().getQuest(id);
            if (quest.isPresent()) {
                new QuestFromItemView(quest.get()).show(player);
            }
            event.setCancelled(true);
        });
    }

}
