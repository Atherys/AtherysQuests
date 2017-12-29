package com.atherys.quests.quest;

import com.atherys.quests.quest.objective.Objective;
import com.atherys.quests.quest.requirement.Requirement;
import com.atherys.quests.quest.reward.Reward;
import org.spongepowered.api.text.Text;

public final class QuestBuilder {

    private Quest quest;

    public QuestBuilder ( String id, int version ) {
        this.quest = new Quest( id, version );
    }

    public QuestBuilder name ( Text name ) {
        quest.setName( name );
        return this;
    }

    public QuestBuilder description ( Text description ) {
        quest.setDescription( description );
        return this;
    }

    public QuestBuilder add ( Requirement requirement ) {
        quest.addRequirement( requirement );
        return this;
    }

    public QuestBuilder add ( Objective objective ) {
        quest.addObjective( objective );
        return this;
    }

    public QuestBuilder add ( Reward reward ) {
        quest.addReward( reward );
        return this;
    }

    public Quest build() {
        return quest;
    }

}
