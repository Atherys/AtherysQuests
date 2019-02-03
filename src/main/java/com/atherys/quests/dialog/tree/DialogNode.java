package com.atherys.quests.dialog.tree;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.api.requirement.Requirement;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
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
    private Text[] npcResponse;

    @Expose
    private String questId;

    @Expose
    private List<DialogNode> responses = new ArrayList<>();

    protected DialogNode(int id) {
        this.id = id;
    }

    public static DialogNodeBuilder builder() {
        return new DialogNodeBuilder();
    }

    public static DialogNodeBuilder builder(int id) {
        return new DialogNodeBuilder(id);
    }

    public List<DialogNode> getResponses() {
        return responses == null ? new ArrayList<>() : responses;
    }

    protected void setResponses(DialogNode... responses) {
        this.responses = Arrays.asList(responses);
    }

    public Text[] getNPCText() {
        return npcResponse;
    }

    protected void setNPCText(Text... NPCText) {
        this.npcResponse = NPCText;
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

    protected void setRequirements(Requirement... requirements) {
        this.requirements = Arrays.asList(requirements);
    }

    public boolean meetsRequirements(Quester player) {
        if (requirements == null) return true;
        for (Requirement requirement : requirements) {
            if (!requirement.check(player)) return false;
        }
        return true;
    }

    public Optional<Quest> getQuest() {
        return AtherysQuests.getInstance().getQuestService().getQuest(questId);
    }

    protected void setQuest(String quest) {
        this.questId = quest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
