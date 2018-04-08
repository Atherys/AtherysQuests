package com.atherys.kotlin.quests

import com.atherys.quests.api.requirement.Requirement
import com.atherys.quests.api.reward.Reward
import com.atherys.quests.quest.Stage
import com.atherys.quests.quest.StagedQuest
import org.spongepowered.api.text.Text

open class StagedQuest(id: String, version: Int) : StagedQuest(id, version) {

    infix fun name(name: Text) {
        setName(name)
    }

    infix fun description(desc: Text) {
        setDescription(desc)
    }

    infix fun require(requirement: Requirement) {
        addRequirement(requirement)
    }

    infix fun requirements(requirements: Array<Requirement>) {
        for (requirement in requirements) {
            addRequirement(requirement)
        }
    }

    infix fun stage(stage: Stage) {
        addStage(stage)
    }

    infix fun stages(stages: Array<Stage>) {
        for (i in stages.indices) {
            addStage(stages[i])
        }
    }

    infix fun reward(reward: Reward) {
        addReward(reward)
    }

    infix fun rewards(rewards: Array<Reward>) {
        for (reward in rewards) {
            addReward(reward)
        }
    }
}