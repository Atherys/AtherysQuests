package com.atherys.quests.quest.requirement;

import ninja.leaping.configurate.objectmapping.Setting;

public abstract class NumericRequirement implements Requirement{

    @Setting
    protected double number;

    protected NumericRequirement ( double amount ) {
        this.number = amount;
    }

    protected boolean check ( double value ) {
        return value >= number;
    }

}
