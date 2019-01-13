package com.atherys.quests.script.lib.reward;

import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.quest.reward.Rewards;
import org.spongepowered.api.service.economy.Currency;

import java.util.function.BiFunction;

/**
 * @jsfunc
 */ 
public class MoneyRewardFunc implements BiFunction<Double, Currency, Reward> {
    /**
     * A reward to give the player money.
     * @jsname moneyReward
     * @param amount The amount of money.
     * @param currency The currency to use.
     */
    @Override
    public Reward apply(Double amount, Currency currency) {
        return Rewards.money(amount, currency);
    }
}
