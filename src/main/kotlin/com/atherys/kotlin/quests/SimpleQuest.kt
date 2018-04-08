package com.atherys.kotlin.quests

import com.atherys.quests.api.objective.Objective
import com.atherys.quests.api.requirement.Requirement
import com.atherys.quests.api.reward.Reward
import com.atherys.quests.quest.SimpleQuest
import org.spongepowered.api.text.Text

open class SimpleQuest(id: String, version: Int) : SimpleQuest(id, version) {

    infix fun SimpleQuest.name(name: Text) {
        setName(name)
    }

    infix fun SimpleQuest.description(desc: Text) {
        setDescription(desc)
    }

    infix fun SimpleQuest.require(requirement: Requirement) {
        addRequirement(requirement)
    }

    infix fun SimpleQuest.requirements(requirements: Array<Requirement>) {
        for (requirement in requirements) {
            addRequirement(requirement)
        }
    }

    infix fun SimpleQuest.objective(objective: Objective<*>) {
        addObjective(objective)
    }

    infix fun SimpleQuest.objectives(objectives: Array<Objective<*>>) {
        for (objective in objectives) {
            addObjective(objective)
        }
    }

    infix fun SimpleQuest.reward(reward: Reward) {
        addReward(reward)
    }

    infix fun SimpleQuest.rewards(rewards: Array<Reward>) {
        for (reward in rewards) {
            addReward(reward)
        }
    }
}