package com.atherys.quests.quest;

import com.atherys.quests.base.CopyUtils;
import com.atherys.quests.base.Observer;
import com.atherys.quests.base.Prototype;
import com.atherys.quests.quest.objective.Objective;
import com.atherys.quests.quest.requirement.Requirement;
import com.atherys.quests.quest.reward.Reward;
import com.atherys.quests.quester.Quester;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.Text;

import java.util.List;

public class Quest implements Prototype<Quest>, Observer {

    private String id;
    private Text name;
    private Text description;

    private List<Requirement> requirements;
    private List<Objective> objectives;
    private List<Reward> rewards;

    protected Quest ( String id ) {
        this.id = id;
        this.name = Text.of( "This quest has no name." );
        this.description = Text.of( "This quest has no description." );
    }

    protected Quest ( Quest quest ) {
        this.id = quest.id;
        this.name = quest.name;
        this.description = quest.description;
        this.requirements = CopyUtils.copyList( quest.getRequirements() );
        this.objectives = CopyUtils.copyList( quest.getObjectives() );
        this.rewards = CopyUtils.copyList( quest.getRewards() );
    }

    public String getId() { return id; }

    public Text getDescription() {
        return description;
    }

    public void setDescription(Text description) {
        this.description = description;
    }

    public Text getName() {
        return name;
    }

    public void setName(Text name) {
        this.name = name;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void addRequirement ( Requirement requirement ) {
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

    public void addObjective ( Objective objective ) {
        if ( !objectives.contains(objective) ) objectives.add( objective );
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public void addReward ( Reward reward ) {
        if ( !rewards.contains( reward ) ) rewards.add( reward );
    }

    public void awardRewards ( Quester player ) {
        rewards.forEach( reward -> reward.award( player ) );
    }

    public void pickup ( Quester quester ) {
        quester.addQuest( this );
    }

    @SuppressWarnings("unchecked")
    public void notify ( Event event ) {
        for ( Objective objective : objectives ) {
            objective.notify ( event );
        }
    }

    public void finish ( Quester quester ) {
        quester.removeQuest ( this );
    }

    @Override
    public Quest copy() {
        return new Quest(this);
    }
}
