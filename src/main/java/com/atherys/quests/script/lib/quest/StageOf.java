package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.quest.Stage;

import java.util.List;
import java.util.function.BiFunction;

/**
 * @jsfunc
 */
public class StageOf implements BiFunction<Objective, List<Reward>, Stage> {
    @Override
    public Stage apply(Objective objective, List<Reward> rewards) {
        return new Stage(objective, rewards);
    }
}
