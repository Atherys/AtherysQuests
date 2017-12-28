package com.atherys.quests.listeners;

import com.atherys.quests.dialog.DialogManager;
import com.atherys.quests.quest.Quest;
import com.atherys.quests.quest.QuestManager;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.quester.QuesterManager;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

public class EntityListener {

    @Listener
    public void onEntityInteract ( InteractEntityEvent event, @Root Player player ) {
        DialogManager.getInstance().startDialog( player, event.getTargetEntity() );
    }

    @Listener
    public void onLeftClick ( InteractBlockEvent.Secondary event, @Root Player player ) {
        Optional<ItemStack> itemInHand = player.getItemInHand ( HandTypes.MAIN_HAND );
        if ( !itemInHand.isPresent() ) return;

        Optional<Quest> quest = QuestManager.getInstance().getQuest(itemInHand.get());
        if ( !quest.isPresent() ) return;

        Optional<Quester> quester = QuesterManager.getInstance().getQuester(player);
        if ( !quester.isPresent() ) return;

        quest.get().pickup( quester.get() );
    }

}
