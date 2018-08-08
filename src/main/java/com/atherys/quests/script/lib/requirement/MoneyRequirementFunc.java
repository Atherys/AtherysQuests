package com.atherys.quests.script.lib.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quest.requirement.Requirements;
import org.spongepowered.api.service.economy.Currency;

import java.util.function.BiFunction;

public class MoneyRequirementFunc implements BiFunction<Double, Currency, Requirement> {
    @Override
    public Requirement apply(Double amount, Currency currency) {
        return Requirements.money(amount, currency);
    }
}
