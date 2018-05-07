package com.atherys.quests.quest;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.quest.AbstractQuest;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.util.CopyUtils;
import com.atherys.quests.util.QuestMsg;
import com.atherys.quests.views.AnyQuestView;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of {@link Quest} which allows the player to complete its objectives in any order they wish.
 */
public class SimpleQuest extends AbstractQuest<SimpleQuest> {

    @Expose
    private List<Requirement> requirements = new ArrayList<>();
    @Expose
    private List<Objective> objectives = new ArrayList<>();
    @Expose
    private List<Reward> rewards = new ArrayList<>();

    @Expose
    private boolean started = false;
    @Expose
    private boolean complete = false;

    protected SimpleQuest(String id, int version) {
        super(id, version);
    }

    protected SimpleQuest(SimpleQuest quest) {
        super(quest.getId(), quest.getVersion(), quest.getName(), quest.getDescription());
        this.requirements = CopyUtils.copyList(quest.getRequirements());
        this.objectives = CopyUtils.copyList(quest.getObjectives());
        this.rewards = CopyUtils.copyList(quest.getRewards());
        this.started = quest.isStarted();
        this.complete = quest.isComplete();
    }

    protected void setDescription(Text description) {
        super.description = description;
    }

    protected void setName(Text name) {
        this.name = name;
    }

    @Override
    public List<Requirement> getRequirements() {
        return requirements;
    }

    protected <T extends Requirement> void addRequirement(T requirement) {
        if(!requirements.contains(requirement)) requirements.add(requirement);
    }

    @Override
    public List<Objective> getObjectives() {
        return objectives;
    }

    protected <T extends Objective> void addObjective(T objective) {
        if(!objectives.contains(objective)) objectives.add(objective);
    }

    @Override
    public List<Reward> getRewards() {
        return rewards;
    }

    protected <T extends Reward> void addReward(T reward) {
        if(!rewards.contains(reward)) rewards.add(reward);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void notify(Event event, Quester quester) {
        // if the quest hasn't been completed yet
        if(!isComplete()) {

            // if the quest hasn't been started yet ( this is the first notification )
            if(!isStarted()) {
                // set it as started
                this.started = true;
            }

            // updated completed status based on the status of the objectives
            for(Objective objective : getObjectives()) {
                if(objective.isComplete()) continue; // if the objective has already been completed, skip it

                objective.notify(event, quester); // notify the objective

                if(objective.isComplete()) { // if the objective is completed after being notified
                    QuestMsg.info(quester, "You have completed an objective for the quest \"", this.getName(), "\""); // tell the player they have completed another objective of the quest

                    // update quest complete status by iterating every objective, checking it's complete status, and concatenate with this.complete
                    this.complete = true;
                    for(Objective objective1 : getObjectives()) {
                        this.complete = this.complete && objective1.isComplete();
                    }
                }
            }

            if(isComplete()) complete(quester);
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
    public SimpleQuest copy() {
        return new SimpleQuest(this);
    }

    @Override
    public AnyQuestView<SimpleQuest> createView() {
        return new AnyQuestView<>(this);
    }
}
