package com.atherys.quests.quest.requirement;

public abstract class NumericRequirement implements Requirement{

    protected double number;

    protected NumericRequirement ( double amount ) {
        this.number = amount;
    }

    protected boolean check ( double value ) {
        return value >= number;
    }

}
