package com.atherys.quests.quest;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.quest.AbstractQuest;
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
 * A Quest which seperates it's Objectives out into Stages, so that they must be completed one-by-one, in an ordered fashion.
 */
public class StagedQuest extends AbstractQuest<StagedQuest> {

    @Expose private List<Requirement> requirements = new ArrayList<>();

    @Expose private List<Stage> stages;
    @Expose private int current;

    @Expose private List<Reward> rewards = new ArrayList<>();

    @Expose
    private boolean started = false;
    @Expose
    private boolean complete = false;

    protected StagedQuest ( String id, int version ) {
        super ( id, version );
    }

    private StagedQuest ( StagedQuest quest ) {
        super ( quest.getId(), quest.getVersion(), quest.getName(), quest.getDescription() );
        this.requirements = CopyUtils.copyList( requirements );
        this.stages = CopyUtils.copyList( quest.getStages() );
        this.rewards = CopyUtils.copyList( rewards );
        this.started = quest.isStarted();
        this.complete = quest.isComplete();
    }

    protected void setName ( Text name ) {
        this.name = name;
    }

    protected void setDescription ( Text description ) {
        this.description = description;
    }

    @Override
    public List<Requirement> getRequirements () {
        return requirements;
    }

    public List<Stage> getStages() {
        return stages;
    }

    protected void addStage ( Stage stage ) {
        stages.add( stage );
    }

    @Override
    public List<Objective> getObjectives () {
        List<Objective> objectives = new ArrayList<>();
        stages.forEach( stage -> objectives.add( stage.getObjective() ) );
        return objectives;
    }

    @Override
    public List<Reward> getRewards () {
        return rewards;
    }

    protected void addReward ( Reward reward ) {
        this.rewards.add( reward );
    }

    @Override
    public void notify ( Event event, Quester quester ) {
        // if the quest has already been completed, just return
        if ( isComplete() ) return;

        // if the current objective is complete
        if ( stages.get( current ).getObjective().isComplete() ) {
            // and has a next objective
            if ( stages.get( current ).getNext() != null ) {
                // set started as true, in case this was the first objective
                this.started = true;
                // award the player for completing the current objective
                stages.get( current ).award( quester );
                // set the current objective to the next one
                current++;

                QuestMsg.info( quester, "You have completed an objective for the quest \"", this.getName(), "\"" );
            // if it does not have a next objective
            } else {
                // set quest as completed
                this.complete = true;
                // return, since there's nothing left to notify
                return;
            }
        }

        // notify the current Stage of the event
        stages.get( current ).notify( event, quester );
    }

    @Override
    public boolean isStarted () {
        return started;
    }

    @Override
    public boolean isComplete () {
        return complete;
    }

    @Override
    public AnyQuestView<StagedQuest> createView () {
        return new AnyQuestView<>( this );
    }

    @Override
    public StagedQuest copy () {
        return new StagedQuest( this );
    }
}
