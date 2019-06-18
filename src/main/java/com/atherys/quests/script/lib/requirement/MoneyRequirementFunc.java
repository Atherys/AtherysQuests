package com.atherys.quests.script.lib.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quest.requirement.Requirements;
import com.atherys.script.api.function.ScriptBiFunction;
import org.spongepowered.api.service.economy.Currency;

/**
 * @jsfunc
 */
public class MoneyRequirementFunc implements ScriptBiFunction<Double, Currency, Requirement> {
    /**
     * A requirement for the player to have a certain amount of money.
     *
     * @param amount   The amount of money.
     * @param currency The money's currency.
     * @jsname moneyRequirement
     */
    @Override
    public Requirement call(Double amount, Currency currency) {
        return Requirements.money(amount, currency);
    }
}
