package com.atherys.quests.api.quest;

import com.atherys.quests.api.quest.modifiers.DeliveryComponent;
import com.atherys.quests.api.quest.modifiers.TimeComponent;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.util.CopyUtils;
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
    protected boolean started;

    @Expose
    protected boolean complete;

    @Expose
    protected List<Requirement> requirements = new ArrayList<>();

    @Expose
    protected List<Reward> rewards = new ArrayList<>();

    @Expose
    protected DeliveryComponent deliveryComponent;

    @Expose
    protected TimeComponent timedComponent;

    protected AbstractQuest(String id, int version) {
        this.id = id;
        this.version = version;
    }

    protected AbstractQuest(String id, @Nullable Text name, @Nullable Text description, int version) {
        this(id, version);
        if (name != null) this.name = name;
        if (description != null) this.description = description;
    }

    protected <E extends Quest> AbstractQuest(AbstractQuest<E> quest) {
        this(quest.getId(), quest.getName(), quest.getDescription(), quest.getVersion());
        this.requirements = CopyUtils.copyList(quest.getRequirements());
        this.rewards = CopyUtils.copyList(quest.getRewards());

        quest.getTimedComponent().ifPresent(timeComponent -> makeTimed(timeComponent.copy()));
        quest.getDeliveryComponent().ifPresent(this::makeDeliverable);
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
        return requirements;
    }

    @Override
    public void award(Quester simpleQuester) {
        for (Reward reward : getRewards()) {
            reward.award(simpleQuester);
        }
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
    public Optional<DeliveryComponent> getDeliveryComponent() {
        return Optional.ofNullable(deliveryComponent);
    }

    @Override
    public void makeDeliverable(DeliveryComponent deliveryComponent) {
        this.deliveryComponent = deliveryComponent;
    }

    @Override
    public Optional<TimeComponent> getTimedComponent() {
        return Optional.ofNullable(timedComponent);
    }

    @Override
    public void makeTimed(TimeComponent timedComponent) {
        this.timedComponent = timedComponent;
    }
}
