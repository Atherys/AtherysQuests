package com.atherys.quests.dialog.requirements;

import com.atherys.quests.AtherysQuests;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;

import java.util.Optional;

public class MoneyRequirement extends DoubleRequirement {

    public MoneyRequirement ( double amount ) {
        super(amount);
    }

    @Override
    public boolean check ( Player player ) {
        Optional<EconomyService> economyService = AtherysQuests.getInstance().getEconomyService();
        if ( economyService.isPresent() ) {
            Optional<UniqueAccount> account = economyService.get().getOrCreateAccount( player.getUniqueId() );
            return account.filter(uniqueAccount -> check(uniqueAccount.getBalance(economyService.get().getDefaultCurrency()).doubleValue())).isPresent();
        } else return false;
    }
}
