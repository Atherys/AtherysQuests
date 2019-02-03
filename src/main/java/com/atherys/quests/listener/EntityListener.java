package com.atherys.quests.listener;

import com.atherys.quests.facade.DialogFacade;
import com.atherys.quests.facade.QuestFacade;
import com.atherys.quests.facade.QuesterFacade;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;

@Singleton
public class EntityListener {

    @Inject
    QuestFacade questFacade;

    @Inject
    DialogFacade dialogFacade;

    @Inject
    QuesterFacade questerFacade;

    EntityListener() {
    }

    @Listener
    public void onEntityInteract(InteractEntityEvent.Secondary.MainHand event, @Root Player player) {
        dialogFacade.onPlayerInteractWithEntity(
                player,
                event.getTargetEntity()
        );
    }

    @Listener
    public void onPlayerMove(MoveEntityEvent e, @Root Player player) {
        questerFacade.onPlayerMoveToQuestRadius(
                e.getFromTransform().getLocation(),
                e.getToTransform().getLocation(),
                player
        );
    }

    @Listener
    public void onBlockInteract(InteractBlockEvent.Secondary event, @Root Player player) {
        questFacade.onBlockInteract(
                event.getTargetBlock().getLocation().get(),
                player
        );
    }

    @Listener
    public void onRightClick(InteractItemEvent.Secondary event, @Root Player player) {
        questFacade.onRightClickItem(
                event,
                event.getItemStack(),
                player
        );
    }

}
