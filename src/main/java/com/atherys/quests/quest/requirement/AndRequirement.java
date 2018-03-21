package com.atherys.quests.quest.requirement;

import com.atherys.quests.quester.Quester;
import org.spongepowered.api.text.Text;

public class AndRequirement implements Requirement {

    private Requirement requirement1;
    private Requirement requirement2;

    public AndRequirement( Requirement requirement1, Requirement requirement2 ) {
        this.requirement1 = requirement1;
        this.requirement2 = requirement2;
    }

    @Override
    public boolean check( Quester quester ) {
        return requirement1.check( quester ) && requirement2.check( quester );
    }

    @Override
    public Requirement copy() {
        return new OrRequirement( requirement1.copy(), requirement2.copy() );
    }

    @Override
    public Text toText() {
        return Text.of( requirement1, " AND ", requirement2 );
    }
}
