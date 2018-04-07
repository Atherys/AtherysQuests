package com.atherys.quests.quest.reward;

import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.service.economy.Currency;

public final class Rewards {

    public static MoneyReward money ( double amount, Currency currency ) {
        return new MoneyReward( amount, currency );
    }

    public static SingleItemReward item ( ItemStack itemStack ) {
        return new SingleItemReward( itemStack );
    }

    public static SingleItemReward item ( ItemStackSnapshot itemStack ) {
        return new SingleItemReward( itemStack );
    }
    
}
