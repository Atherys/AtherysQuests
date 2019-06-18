package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.quest.Stage;
import com.atherys.script.api.function.ScriptBiFunction;

import java.util.List;

/**
 * @jsfunc
 */
public class StageOf implements ScriptBiFunction<Objective, List<Reward>, Stage> {
    @Override
    public Stage call(Objective objective, List<Reward> rewards) {
        return new Stage(objective, rewards);
    }
}
