package com.atherys.quests.quest.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.google.gson.annotations.Expose;

public abstract class NumericRequirement implements Requirement {

    @Expose
    protected double number;

    protected NumericRequirement( double amount ) {
        this.number = amount;
    }

    protected boolean check( double value ) {
        return value >= number;
    }

}
