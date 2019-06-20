package com.atherys.quests.dialog.tree;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DialogNode {

    @Expose
    private int id;

    @Expose
    private List<Requirement> requirements = new ArrayList<>();

    @Expose
    private Text playerText;

    @Expose
    private List<Text> npcResponse;

    @Expose
    private String questId;

    @Expose
    private String completesQuestId;

    @Expose
    private List<DialogNode> responses = new ArrayList<>();

    @Expose
    private boolean hidden = false;

    protected DialogNode(int id) {
        this.id = id;
    }

    public static DialogNodeBuilder builder() {
        return new DialogNodeBuilder();
    }

    public static DialogNodeBuilder builder(int id) {
        return new DialogNodeBuilder(id);
    }

    public static DialogNodeBuilder builder(DialogNode node) {
        return new DialogNodeBuilder(node);
    }

    public List<DialogNode> getResponses() {
        return responses == null ? new ArrayList<>() : responses;
    }

    protected void setResponses(List<DialogNode> responses) {
        this.responses = responses;
    }

    public List<Text> getNPCText() {
        return npcResponse;
    }

    protected void setNPCText(List<Text> npcText) {
        this.npcResponse = npcText;
    }

    public Text getPlayerText() {
        return playerText;
    }

    protected void setPlayerText(Text playerText) {
        this.playerText = playerText;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    protected void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    public Optional<Quest> getQuest() {
        return AtherysQuests.getInstance().getQuestService().getQuest(questId);
    }

    protected void setQuest(String quest) {
        this.questId = quest;
    }

    public Optional<Quest> getCompletesQuest() {
        return AtherysQuests.getInstance().getQuestService().getQuest(completesQuestId);
    }

    protected void setCompletesQuest(String quest) {
        this.completesQuestId = quest;
    }

    public boolean isHidden() {
        return hidden;
    }

    protected void hide() {
        this.hidden = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
