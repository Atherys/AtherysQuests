package com.atherys.quests.api.quest;

import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.api.quest.modifiers.Deliverable;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractQuest<T extends Quest> implements Quest<T> {

    @Expose
    protected String id;

    @Expose
    protected Text name = Text.of("Unnamed Quest");

    @Expose
    protected Text description = Text.of("No Description");

    @Expose
    protected int version;

    @Expose
    protected boolean failed;

    @Expose
    protected boolean started;

    @Expose
    protected boolean complete;

    @Expose
    protected List<Requirement> requirements = new ArrayList<>();

    @Expose
    protected Deliverable deliverableComponent;

    protected AbstractQuest(String id, int version) {
        this.id = id;
        this.version = version;
        failed = false;
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
    public List<Requirement> getRequirements() {
        return  requirements;
    }

    @Override
    public void award(Quester simpleQuester) {
        for (Reward reward : getRewards()) {
            reward.award(simpleQuester);
        }
    }

    public void fail() {
        failed = true;
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public Optional<Deliverable> getDeliverableComponent() {
        return Optional.ofNullable(deliverableComponent);
    }

    @Override
    public void makeDeliverable(Deliverable deliverable) {
        this.deliverableComponent = deliverable;
    }
}
