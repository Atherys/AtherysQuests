package com.atherys.quests.quest;

import com.atherys.quests.quest.requirement.Requirement;
import com.atherys.quests.quest.reward.Reward;
import com.atherys.quests.quester.Quester;
import org.spongepowered.api.text.Text;

import java.util.List;

public abstract class Quest {

    private String id;
    private Text name;
    private Text description;

    private List<Requirement> requirements;
    private List<Reward> rewards;

    protected Quest ( String id ) {
        this.id = id;
        this.name = Text.of( "This quest has no name." );
        this.description = Text.of( "This quest has no description." );
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

    public List<Reward> getRewards() {
        return rewards;
    }

    public void addReward ( Reward reward ) {
        if ( !rewards.contains( reward ) ) rewards.add( reward );
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

    public void awardRewards ( Quester player ) {
        rewards.forEach( reward -> reward.award( player ) );
    }

    public abstract void pickup ( Quester quester );

    public abstract void finish ( Quester quester );
}
