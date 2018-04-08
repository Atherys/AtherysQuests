package com.atherys.quests.quest.reward;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.quester.Quester;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.text.Text;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * If an {@link EconomyService} is present, this reward will give the player a certain amount of {@link Currency}
 */
public class MoneyReward implements Reward {

    @Expose
    private double amount;
    @Expose
    private Currency currency;

    MoneyReward( double amount, Currency currency ) {
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public boolean award( Quester quester ) {
        Optional<? extends User> user = quester.getUser();
        if ( !user.isPresent() ) return false;

        Optional<EconomyService> service = AtherysQuests.getInstance().getEconomyService();
        if ( !service.isPresent() ) return false;

        Optional<UniqueAccount> account = service.get().getOrCreateAccount( user.get().getUniqueId() );
        return account.filter( uniqueAccount -> uniqueAccount.deposit( currency, BigDecimal.valueOf( amount ), Sponge.getCauseStackManager().getCurrentCause() ).getResult().equals( ResultType.SUCCESS ) ).isPresent();
    }

    @Override
    public Reward copy() {
        return new MoneyReward( this.amount, this.currency );
    }

    @Override
    public Text toText() {
        return Text.of( currency.format( BigDecimal.valueOf( amount ) ) );
    }
}
