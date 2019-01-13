package com.atherys.quests.script.lib.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quest.requirement.Requirements;
import org.spongepowered.api.service.economy.Currency;

import java.util.function.BiFunction;

/**
 * @jsfunc
 */ 
public class MoneyRequirementFunc implements BiFunction<Double, Currency, Requirement> {
    /**
     * A requirement for the player to have a certain amount of money.
     * @jsname moneyRequirement
     * @param amount The amount of money.
     * @param currency The money's currency.
     */
    @Override
    public Requirement apply(Double amount, Currency currency) {
        return Requirements.money(amount, currency);
    }
}
