package com.atherys.quests.quest;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.quest.AbstractQuest;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.event.quest.QuestCompletedEvent;
import com.atherys.quests.event.quest.QuestStartedEvent;
import com.atherys.quests.event.quest.StagedQuestProgressEvent;
import com.atherys.quests.util.CopyUtils;
import com.atherys.quests.views.StagedQuestView;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A Quest which seperates it's Objectives out into Stages, so that they must be completed one-by-one, in an ordered fashion.
 */
public class StagedQuest extends AbstractQuest<StagedQuest> {

    @Expose
    private List<Stage> stages = new ArrayList<>();

    @Expose
    private int current = 0;

    public StagedQuest(String id, Text name, Text description, int version) {
        super(id, name, description, version);
    }

    StagedQuest(StagedQuest quest) {
        super(quest);
        this.stages = CopyUtils.copyList(quest.getStages());
    }

    public void addRequirement(Requirement requirement) {
        requirements.add(requirement);
    }

    public List<Stage> getStages() {
        return stages;
    }

    public Stage getCurrent() {
        return stages.get(current);
    }

    protected void addStage(Stage stage) {
        stages.add(stage);
    }

    public Stage getNextStage() {
        return stages.get(current + 1);
    }

    public boolean hasNextStage() {
        return stages.size() > current + 1;
    }

    @Override
    public List<Objective> getObjectives() {
        List<Objective> objectives = new ArrayList<>();
        stages.forEach(stage -> objectives.add(stage.getObjective()));
        return objectives;
    }

    @Override
    public List<Reward> getRewards() {
        return rewards;
    }

    @Override
    public void notify(Event event, Quester quester) {
        // if the Quest has already been completed, just return
        if (isComplete()) return;

        // set started as true, in case this was the first objective
        if (!isStarted()) {
            this.started = true;
            Sponge.getEventManager().post(new QuestStartedEvent(this, quester));
        }

        Stage currentStage = stages.get(current);

        // notify the current stage of the event
        currentStage.notify(event, quester);

        if (currentStage.isComplete()) {

            // award the player for completing the current stage
            currentStage.award(quester);

            // and has a next stage
            if (this.hasNextStage()) {

                // set the current stage to the next one
                current++;
                AtherysQuests.getInstance().getQuestMessagingService().info(quester, "You have completed an objective for the Quest \"", this.getName(), "\"");

                Sponge.getEventManager().post(new StagedQuestProgressEvent(this, currentStage, quester));
                // if it does not have a next stage
            } else {
                // set Quest as completed
                this.complete = true;
                Sponge.getEventManager().post(new QuestCompletedEvent(this, quester));
            }
        }
    }

    @Override
    public StagedQuestView createView() {
        return new StagedQuestView(this);
    }

    @Override
    public StagedQuest copy() {
        return new StagedQuest(this);
    }
}
