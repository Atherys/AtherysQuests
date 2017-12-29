package com.atherys.quests.quest;

import com.atherys.quests.base.Observer;
import com.atherys.quests.base.Prototype;
import com.atherys.quests.events.QuestCompletedEvent;
import com.atherys.quests.events.QuestStartedEvent;
import com.atherys.quests.quest.objective.Objective;
import com.atherys.quests.quest.requirement.Requirement;
import com.atherys.quests.quest.reward.Reward;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.util.CopyUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.Text;

import java.util.List;

public class Quest implements Prototype<Quest>, Observer {

    private String id;
    private Text name;
    private Text description;
    private int version;

    private List<Requirement> requirements;
    private List<Objective> objectives;
    private List<Reward> rewards;

    private boolean started  = false;
    private boolean complete = false;

    protected Quest ( String id, int version ) {
        this.id = id;
        this.name = Text.of( "This quest has no name." );
        this.description = Text.of( "This quest has no description." );
        this.version = version;
    }

    protected Quest ( Quest quest ) {
        this.id = quest.getId();
        this.name = quest.getName();
        this.description = quest.getDescription();
        this.version = quest.getVersion();
        this.requirements = CopyUtils.copyList( quest.getRequirements() );
        this.objectives = CopyUtils.copyList( quest.getObjectives() );
        this.rewards = CopyUtils.copyList( quest.getRewards() );
        this.complete = quest.isComplete();
    }

    public static QuestBuilder builder(String id, int version) {
        return new QuestBuilder( id, version );
    }

    public String getId() { return id; }

    public Text getDescription() {
        return description;
    }

    protected void setDescription(Text description) {
        this.description = description;
    }

    public Text getName() {
        return name;
    }

    protected void setName(Text name) {
        this.name = name;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    protected void addRequirement ( Requirement requirement ) {
        if ( !requirements.contains( requirement ) ) requirements.add( requirement );
    }

    public boolean meetsRequiements ( Quester player ) {
        for ( Requirement req : requirements ) {
            if ( !req.check(player) ) return false;
        }
        return true;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    protected void addObjective ( Objective objective ) {
        if ( !objectives.contains(objective) ) objectives.add( objective );
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    protected void addReward ( Reward reward ) {
        if ( !rewards.contains( reward ) ) rewards.add( reward );
    }

    public void awardRewards ( Quester player ) {
        rewards.forEach( reward -> reward.award( player ) );
    }

    @Override
    @SuppressWarnings("unchecked")
    public void notify ( Event event, Quester quester ) {
        // if the quest hasn't been started yet ( this is the first notification
        if ( !isStarted() ) {
            // set it as started
            this.started = true;
            QuestStartedEvent qsEvent = new QuestStartedEvent( this, quester );
            Sponge.getEventManager().post( qsEvent );
        }

        // if the quest hasn't been completed yet
        if ( !isComplete() ) {
            // set it as completed
            this.complete = true;

            // updated completed status based on the status of the objectives
            for ( Objective objective : objectives ) {
                objective.notify ( event, quester );
                if ( !objective.isComplete() ) this.complete = false;
            }

            // if all the objectives are completed
            if ( isComplete() ) {
                QuestCompletedEvent qsEvent = new QuestCompletedEvent( this, quester );
                Sponge.getEventManager().post( qsEvent );
            }
        }
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isComplete () {
        return complete;
    }

    @Override
    public Quest copy() {
        return new Quest(this);
    }

    public int getVersion() {
        return version;
    }
}
