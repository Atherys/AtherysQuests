package com.atherys.quests.quest.requirement;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quester.Quester;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.text.Text;

import java.math.BigDecimal;
import java.util.Optional;

public class MoneyRequirement extends NumericRequirement {

    @Expose
    private Currency currency;

    private MoneyRequirement() {
        super( 0 );
    }

    MoneyRequirement( double amount, Currency currency ) {
        super( amount );
        this.currency = currency;
    }

    @Override
    public boolean check( Quester quester ) {
        Optional<? extends User> user = quester.getUser();
        if ( !user.isPresent() ) return false;

        Optional<EconomyService> service = AtherysQuests.getInstance().getEconomyService();
        if ( !service.isPresent() ) return false;

        Optional<UniqueAccount> account = service.get().getOrCreateAccount( user.get().getUniqueId() );
        return account.filter( uniqueAccount -> check( uniqueAccount.getBalance( currency ).doubleValue() ) ).isPresent();

    }

    @Override
    public Requirement copy() {
        return new MoneyRequirement( this.number, this.currency );
    }

    @Override
    public Text toText() {
        return Text.of( "You must have at least ", currency.format( BigDecimal.valueOf( super.number ) ) );
    }
}
