package com.atherys.quests.listeners;

import com.atherys.quests.dialog.DialogManager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;

public class EntityListener {

    @Listener
    public void onEntityInteract ( InteractEntityEvent event, @Root Player player ) {
        DialogManager.getInstance().startDialog( player, event.getTargetEntity() );
    }

}
