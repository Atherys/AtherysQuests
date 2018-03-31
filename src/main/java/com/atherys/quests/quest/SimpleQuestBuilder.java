package com.atherys.quests.quest;

import com.atherys.quests.quest.objective.Objective;
import com.atherys.quests.quest.requirement.Requirement;
import com.atherys.quests.quest.reward.Reward;
import org.spongepowered.api.text.Text;

public final class SimpleQuestBuilder {

    private SimpleQuest quest;

    public SimpleQuestBuilder( String id, int version ) {
        this.quest = new SimpleQuest( id, version );
    }

    public SimpleQuestBuilder name( Text name ) {
        quest.setName( name );
        return this;
    }

    public SimpleQuestBuilder description( Text description ) {
        quest.setDescription( description );
        return this;
    }

    public SimpleQuestBuilder add( Requirement requirement ) {
        quest.addRequirement( requirement );
        return this;
    }

    public SimpleQuestBuilder add( Objective objective ) {
        quest.addObjective( objective );
        return this;
    }

    public SimpleQuestBuilder add( Reward reward ) {
        quest.addReward( reward );
        return this;
    }

    public Quest build() {
        return quest;
    }

}
