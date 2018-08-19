package com.atherys.quests.quest.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quester.Quester;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;

/**
 * A requirement which negates the check of another requirement.
 */
public class NotRequirement implements Requirement {

    @Expose
    private Requirement requirement;


    NotRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    @Override
    public boolean check(Quester quester) {
        return !requirement.check(quester);
    }

    @Override
    public Requirement copy() {
        return new NotRequirement(requirement.copy());
    }

    @Override
    public Text toText() {
        return Text.of("NOT ", requirement.toText());
    }
}
