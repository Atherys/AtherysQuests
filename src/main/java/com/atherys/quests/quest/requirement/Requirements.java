package com.atherys.quests.quest.requirement;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;
import org.spongepowered.api.service.economy.Currency;

/**
 * A factory class for accessing the default Requirements.
 */
public final class Requirements {

    public static AndRequirement and ( Requirement first, Requirement second ) {
        return new AndRequirement( first, second );
    }

    public static OrRequirement or ( Requirement first, Requirement second ) {
        return new OrRequirement( first, second );
    }

    public static NotRequirement not ( Requirement requirement ) {
        return new NotRequirement( requirement );
    }

    public static QuestRequirement quest ( String questId ) {
        return new QuestRequirement( questId );
    }

    public static QuestRequirement quest ( Quest quest ) {
        return new QuestRequirement( quest );
    }

    public static MoneyRequirement money ( double amount, Currency currency ) {
        return new MoneyRequirement( amount, currency );
    }

    public static LevelRequirement level ( int level ) {
        return new LevelRequirement( level );
    }

}
