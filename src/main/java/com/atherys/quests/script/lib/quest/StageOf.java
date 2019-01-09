package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.model.quest.Stage;

import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * @jsfunc
 */ 
public class StageOf implements BiFunction<Objective, Reward[], Stage> {
    @Override
    public Stage apply(Objective objective, Reward[] rewards) {
        return new Stage(objective, Arrays.asList(rewards));
    }
}
