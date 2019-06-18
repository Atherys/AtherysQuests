package com.atherys.quests.script.lib.reward;

import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.quest.reward.Rewards;
import com.atherys.script.api.function.ScriptBiFunction;
import org.spongepowered.api.service.economy.Currency;

/**
 * @jsfunc
 */
public class MoneyRewardFunc implements ScriptBiFunction<Double, Currency, Reward> {
    /**
     * A reward to give the player money.
     *
     * @param amount   The amount of money.
     * @param currency The currency to use.
     * @jsname moneyReward
     */
    @Override
    public Reward call(Double amount, Currency currency) {
        return Rewards.money(amount, currency);
    }
}
