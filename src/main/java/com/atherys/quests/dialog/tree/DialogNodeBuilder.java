package com.atherys.quests.dialog.tree;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quest.requirement.Requirements;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DialogNodeBuilder {

    DialogNode node;

    private List<Requirement> requirementsList;
    private List<DialogNode> responses;
    private List<Text> npc;

    public DialogNodeBuilder() {
        this(-1);
    }

    public DialogNodeBuilder(int id) {
        node = new DialogNode(id);
        requirementsList = new ArrayList<>();
        responses = new ArrayList<>();
        npc = new ArrayList<>();
    }

    public DialogNodeBuilder id(int id) {
        node.setId(id);
        return this;
    }

    public DialogNodeBuilder(DialogNode fromNode) {
        this(fromNode.getId());
        node.setPlayerText(fromNode.getPlayerText());
        node.setNPCText(fromNode.getNPCText());
        node.setResponses(fromNode.getResponses());
        node.setRequirements(fromNode.getRequirements());
        if (fromNode.getQuest().isPresent()) {
            fromNode.setQuest(fromNode.getQuest().get().getId());
        }
    }

    public DialogNodeBuilder player(Text text) {
        node.setPlayerText(text);
        return this;
    }

    public DialogNodeBuilder npc(List<Text> text) {
        npc.addAll(text);
        return this;
    }

    public DialogNodeBuilder quest(Quest quest) {
        node.setQuest(quest.getId());
        return this;
    }

    public DialogNodeBuilder completes(Quest quest) {
        node.setCompletesQuest(quest.getId());
        requirementsList.add(Requirements.completedQuest(quest));
        return this;
    }

    public DialogNodeBuilder requirements(List<Requirement> requirements) {
        requirementsList.addAll(requirements);
        return this;
    }

    public DialogNodeBuilder responses(List<DialogNode> nodes) {
        responses.addAll(nodes);
        return this;
    }

    public DialogNodeBuilder hide() {
        node.hide();
        return this;
    }

    public DialogNode build() {
        if (node.getId() == -1)
            throw new IllegalStateException("DialogNode requires a valid int id. 0 is preferred for root nodes.");
        if (node.getId() != 0 && node.getPlayerText() == null)
            throw new IllegalStateException("DialogNode cannot have an empty player text unless it is a root node ( it's id = 0 ).");
        if (node.getResponses().size() != 0 && node.getNPCText() == null)
            throw new IllegalStateException("DialogNode cannot have empty NPC text unless it is a leaf node.");
        node.setNPCText(npc);
        node.setRequirements(requirementsList);
        node.setResponses(responses);
        return node;
    }

}
