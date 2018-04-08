package com.atherys.kotlin.quests

import com.atherys.quests.api.requirement.Requirement
import com.atherys.quests.api.reward.Reward
import com.atherys.quests.quest.Stage
import com.atherys.quests.quest.StagedQuest
import org.spongepowered.api.text.Text

open class StagedQuest(id: String, version: Int) : StagedQuest(id, version) {

    infix fun StagedQuest.name(name: Text) {
        setName(name)
    }

    infix fun StagedQuest.description(desc: Text) {
        setDescription(desc)
    }

    infix fun StagedQuest.require(requirement: Requirement) {
        addRequirement(requirement)
    }

    infix fun StagedQuest.requirements(requirements: Array<Requirement>) {
        for (requirement in requirements) {
            addRequirement(requirement)
        }
    }

    infix fun StagedQuest.stage(stage: Stage) {
        addStage(stage)
    }

    infix fun StagedQuest.stages(stages: Array<Stage>) {
        for (i in stages.indices) {
            addStage(stages[i])
        }
    }

    infix fun StagedQuest.reward(reward: Reward) {
        addReward(reward)
    }

    infix fun StagedQuest.rewards(rewards: Array<Reward>) {
        for (reward in rewards) {
            addReward(reward)
        }
    }
}