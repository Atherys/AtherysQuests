package com.atherys.quests.views;

import com.atherys.quests.api.quest.Quest;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.item.inventory.ItemStack;

public class QuestFromItemView extends TakeQuestView {
    public QuestFromItemView(Quest<?> quest) {
        super(quest);
        setOnAccept(player -> player.setItemInHand(HandTypes.MAIN_HAND, ItemStack.empty()));
    }
}
