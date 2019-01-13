package com.atherys.quests.api.quest;

import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.api.reward.Reward;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

public abstract class AbstractQuest<T extends Quest> implements Quest<T> {

    @Expose
    protected String id;

    @Expose
    protected Text name = Text.of("Unnamed Quest");

    @Expose
    protected Text description = Text.of("No Description");

    @Expose
    protected int version;

    protected AbstractQuest(String id, int version) {
        this.id = id;
        this.version = version;
    }

    protected AbstractQuest(String id, int version, @Nullable Text name, @Nullable Text description) {
        this(id, version);
        if (name != null) this.name = name;
        if (description != null) this.description = description;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Text getName() {
        return name;
    }

    @Override
    public Text getDescription() {
        return description;
    }

    @Override
    public boolean meetsRequirements(Quester simpleQuester) {
        for (Requirement req : getRequirements()) {
            if (!req.check(simpleQuester)) return false;
        }
        return true;
    }

    @Override
    public void award(Quester simpleQuester) {
        for (Reward reward : getRewards()) {
            reward.award(simpleQuester);
        }
    }

    @Override
    public int getVersion() {
        return version;
    }
}
