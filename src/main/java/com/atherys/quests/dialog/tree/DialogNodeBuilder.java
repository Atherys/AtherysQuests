package com.atherys.quests.dialog.tree;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quest.requirement.Requirements;
import com.google.common.collect.Iterables;
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
        node.setResponses(fromNode.getResponses().toArray(new DialogNode[0]));
        node.setRequirements(fromNode.getRequirements().toArray(new Requirement[0]));
        if (fromNode.getQuest().isPresent()) {
            fromNode.setQuest(fromNode.getQuest().get().getId());
        }
    }

    public DialogNodeBuilder player(Text text) {
        node.setPlayerText(text);
        return this;
    }

    public DialogNodeBuilder npc(Text... text) {
        npc.addAll(Arrays.asList(text));
        return this;
    }

    public DialogNodeBuilder quest(Quest quest) {
        node.setQuest(quest.getId());
        return this;
    }

    public DialogNodeBuilder completes(Quest quest) {
        node.setCompletesQuest(quest.getId());
        requirements(Requirements.completedQuest(quest));
        return this;
    }

    public DialogNodeBuilder requirements(Requirement... requirements) {
        requirementsList.addAll(Arrays.asList(requirements));
        return this;
    }

    public DialogNodeBuilder responses(DialogNode... nodes) {
        responses.addAll(Arrays.asList(nodes));
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
        node.setRequirements(Iterables.toArray(requirementsList, Requirement.class));
        node.setResponses(Iterables.toArray(responses, DialogNode.class));
        node.setNPCText(Iterables.toArray(npc, Text.class));
        return node;
    }

}
