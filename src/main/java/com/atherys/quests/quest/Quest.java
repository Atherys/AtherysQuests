package com.atherys.quests.quest;

import com.atherys.core.views.Viewable;
import com.atherys.quests.base.Observer;
import com.atherys.quests.base.Prototype;
import com.atherys.quests.quest.objective.Objective;
import com.atherys.quests.quest.requirement.Requirement;
import com.atherys.quests.quest.reward.Reward;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.util.CopyUtils;
import com.atherys.quests.views.QuestView;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Quest implements Prototype<Quest>, Observer, Viewable<QuestView> {

    @Expose
    private String id;
    @Expose
    private Text name;
    @Expose
    private Text description;
    @Expose
    private int version;

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

    private Quest() {
    }

    protected Quest( String id, int version ) {
        this.id = id;
        this.name = Text.of( "This quest has no name." );
        this.description = Text.of( "This quest has no description." );
        this.version = version;
    }

    protected Quest( Quest quest ) {
        this.id = quest.getId();
        this.name = quest.getName();
        this.description = quest.getDescription();
        this.version = quest.getVersion();
        this.requirements = CopyUtils.copyList( quest.getRequirements() );
        this.objectives = CopyUtils.copyList( quest.getObjectives() );
        this.rewards = CopyUtils.copyList( quest.getRewards() );
        this.complete = quest.isComplete();
    }

    public static QuestBuilder builder( String id, int version ) {
        return new QuestBuilder( id, version );
    }

    public String getId() {
        return id;
    }

    public Text getDescription() {
        return description;
    }

    protected void setDescription( Text description ) {
        this.description = description;
    }

    public Text getName() {
        return name;
    }

    protected void setName( Text name ) {
        this.name = name;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    protected <T extends Requirement> void addRequirement( T requirement ) {
        if ( !requirements.contains( requirement ) ) requirements.add( requirement );
    }

    public boolean meetsRequiements( Quester player ) {
        for ( Requirement req : requirements ) {
            if ( !req.check( player ) ) return false;
        }
        return true;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    protected <T extends Objective> void addObjective( T objective ) {
        if ( !objectives.contains( objective ) ) objectives.add( objective );
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    protected <T extends Reward> void addReward( T reward ) {
        if ( !rewards.contains( reward ) ) rewards.add( reward );
    }

    public void awardRewards( Quester player ) {
        rewards.forEach( reward -> reward.award( player ) );
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public void notify( Event event, Quester quester ) {
        // if the quest hasn't been started yet ( this is the first notification )
        if ( !isStarted() ) {
            // set it as started
            this.started = true;
        }

        // if the quest hasn't been completed yet
        if ( !isComplete() ) {

            // updated completed status based on the status of the objectives
            for ( Objective objective : getObjectives() ) {
                if ( objective.isComplete() ) continue; // if the objective has already been completed, skip it

                objective.notify( event, quester ); // notify the objective

                if ( objective.isComplete() ) { // if the objective is completed after being notified
                    QuestMsg.info( quester, "You have completed an objective for the quest \"", this.getName(), "\"" ); // tell the player they have completed another objective of the quest

                    // update quest complete status by iterating every objective, checking it's complete status, and concatenating with this.complete
                    this.complete = true;
                    for ( Objective objective1 : getObjectives() ) {
                        this.complete = this.complete && objective1.isComplete();
                    }
                }
            }
        }
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isComplete() {
        return complete;
    }

    @Override
    public Quest copy() {
        return new Quest( this );
    }

    public int getVersion() {
        return version;
    }

    @Override
    public Optional<QuestView> createView() {
        return Optional.of( new QuestView( this ) );
    }
}
