package com.atherys.quests.dialog.requirements;

public abstract class DoubleRequirement implements DialogRequirement {

    private double amount;

    public DoubleRequirement ( double amount ) {
        this.amount = amount;
    }

    public boolean check ( double value ) {
        return value >= amount;
    }

}
