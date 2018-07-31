package com.atherys.quests.quest;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.quest.AbstractQuest;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.event.quest.QuestCompletedEvent;
import com.atherys.quests.event.quest.QuestStartedEvent;
import com.atherys.quests.event.quest.StagedQuestProgressEvent;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.util.CopyUtils;
import com.atherys.quests.util.QuestMsg;
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
    private List<Requirement> requirements = new ArrayList<>();

    @Expose
    private List<Stage> stages = new ArrayList<>();
    @Expose
    private int current = 0;

    @Expose
    private List<Reward> rewards = new ArrayList<>();

    @Expose
    private boolean started = false;
    @Expose
    private boolean complete = false;

    public StagedQuest(String id, Text name, Text description, int version) {
        super(id, version);
        this.setName(name);
        this.setDescription(description);
    }

    private StagedQuest(StagedQuest quest) {
        super(quest.getId(), quest.getVersion(), quest.getName(), quest.getDescription());
        this.requirements = CopyUtils.copyList(quest.getRequirements());
        this.stages = CopyUtils.copyList(quest.getStages());
        this.rewards = CopyUtils.copyList(quest.getRewards());
        this.started = false;
        this.complete = false;
    }

    protected void setName(Text name) {
        this.name = name;
    }

    protected void setDescription(Text description) {
        this.description = description;
    }

    @Override
    public List<Requirement> getRequirements() {
        return requirements;
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

    protected void addReward(Reward reward) {
        this.rewards.add(reward);
    }

    @Override
    public void notify(Event event, Quester quester) {
        // if the quest has already been completed, just return
        if (isComplete()) return;

        // set started as true, in case this was the first objective
        if (!isStarted()) {
            this.started = true;
            Sponge.getEventManager().post(new QuestStartedEvent(this, quester));
        }

        Stage currentStage = stages.get(current);

        // notify the current stage of the event
        currentStage.notify(event, quester);

        // if the current stage is complete
        if (currentStage.isComplete()) {

            // award the player for completing the current stage
            currentStage.award(quester);

            // and has a next stage
            if (this.hasNextStage()) {

                // set the current stage to the next one
                current++;
                QuestMsg.info(quester, "You have completed an objective for the quest \"", this.getName(), "\"");

                Sponge.getEventManager().post(new StagedQuestProgressEvent(this, currentStage, quester));
                // if it does not have a next stage
            } else {
                // set quest as completed
                this.complete = true;
                Sponge.getEventManager().post(new QuestCompletedEvent(this, quester));
            }
        }
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public boolean isComplete() {
        return complete;
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
