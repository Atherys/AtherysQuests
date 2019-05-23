package com.atherys.quests.quest;

import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.quest.Deliverable;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.views.DeliverableQuestView;
import com.atherys.quests.views.QuestView;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.UUID;

public class DeliverableQuest<T extends Quest> implements Quest<DeliverableQuest>, Deliverable {

    @Expose
    private T quest;

    @Expose
    private UUID target;

    @Expose
    private Text targetName;

    private DialogNode node;

    public DeliverableQuest(T quest, DialogNode node, UUID target, Text targetName) {
        this(quest, target, targetName);
        this.node = node;
    }

    public DeliverableQuest(T quest, UUID target, Text targetName) {
        if (quest instanceof DeliverableQuest) {
            throw new IllegalArgumentException("Cannot create DeliverableQuest of itself!");
        }
        this.quest = quest;
        this.target = target;
        this.targetName = targetName;
    }

    public DialogNode getNode() {
        return node;
    }

    public T getQuest() {
        return quest;
    }

    @Override
    public String getId() {
        return quest.getId();
    }

    @Override
    public Text getName() {
        return quest.getName();
    }

    @Override
    public Text getDescription() {
        return quest.getDescription();
    }

    @Override
    public List<Requirement> getRequirements() {
        return quest.getRequirements();
    }

    @Override
    public List<Objective> getObjectives() {
        return quest.getObjectives();
    }

    @Override
    public List<Reward> getRewards() {
        return quest.getRewards();
    }

    @Override
    public boolean meetsRequirements(Quester quester) {
        return quest.meetsRequirements(quester);
    }

    @Override
    public void notify(Event event, Quester quester) {
        quest.notify(event, quester);
    }

    @Override
    public void award(Quester quester) {
        quest.award(quester);
    }

    @Override
    public boolean isStarted() {
        return quest.isStarted();
    }

    @Override
    public boolean isComplete() {
        return quest.isComplete();
    }

    @Override
    public boolean isFailed() {
        return quest.isFailed();
    }

    @Override
    public int getVersion() {
        return quest.getVersion();
    }

    @Override
    public QuestView createView() {
        return new DeliverableQuestView<QuestView, DeliverableQuest>(quest.createView(), this);
    }

    @Override
    public DeliverableQuest<T> copy() {
        return new DeliverableQuest<>(quest, node, target, targetName);
    }

    @Override
    public UUID getTarget() {
        return target;
    }

    @Override
    public Text getTargetName() {
        return targetName;
    }

}
