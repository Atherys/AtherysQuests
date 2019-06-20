package com.atherys.quests.listener;

import com.atherys.quests.event.dialog.DialogProceedEvent;
import com.atherys.quests.facade.QuesterFacade;
import com.atherys.quests.util.EntityUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;

@Singleton
public class InternalListener {

    @Inject
    QuesterFacade questerFacade;

    @Listener
    public void onDialogProceed(DialogProceedEvent event, @Root Player player) {
        questerFacade.notifyAndUpdateCachedPlayer(event, player);
    }

    @Listener
    public void onInteractBlock(InteractBlockEvent event, @Root Player player) {
        questerFacade.notifyAndUpdateCachedPlayer(event, player);
    }

    @Listener
    public void onEntityInteract(InteractEntityEvent.Secondary event, @Root Player player) {
        questerFacade.notifyAndUpdateCachedPlayer(event, player);
    }

    @Listener
    public void onEntityDeath(DestructEntityEvent.Death event, @Root EntityDamageSource source) {
        EntityUtils.playerKilledEntity(source).ifPresent(player -> {
            questerFacade.notifyAndUpdateCachedPlayer(event, player);
        });
    }

    @Listener
    public void onEntityMove(MoveEntityEvent event, @Root Player player) {
        questerFacade.notifyAndUpdateCachedPlayer(event, player);
    }
}
