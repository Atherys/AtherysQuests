package com.atherys.quests.quest.reward;

import com.atherys.quests.AtherysQuests;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;

import java.util.List;

/**
 * A factory class for accessing the default Rewards.
 */
public final class Rewards {

    public static MoneyReward money(double amount, Currency currency) {
        return new MoneyReward(amount, currency);
    }

    public static MoneyReward money(double amount, String currencyId) {
        Currency currency = Sponge.getRegistry().getType(Currency.class, currencyId).orElse(AtherysQuests.getInstance().getEconomyService().get().getDefaultCurrency());
        return new MoneyReward(amount, currency);
    }

    public static ItemsReward items(Text description, List<ItemStack> itemStacks) {
        return new ItemsReward(description, itemStacks);
    }

    public static CommandReward command(String command, Text description) {
        return new CommandReward(command, description);
    }

}
