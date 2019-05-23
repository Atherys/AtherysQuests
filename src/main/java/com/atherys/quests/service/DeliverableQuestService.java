package com.atherys.quests.service;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogTree;
import com.atherys.quests.event.dialog.DialogProceedEvent;
import com.atherys.quests.quest.DeliverableQuest;
import com.atherys.quests.quest.requirement.Requirements;
import com.atherys.quests.util.EntityUtils;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.EventListener;

import java.util.List;
import java.util.Optional;

@Singleton
public class DeliverableQuestService {
    @Inject
    DialogService dialogService;

    @Inject
    QuestService questService;

    public void applyNodesForQuests() {
        questService.getDeliverableQuests().forEach(deliverableQuest -> {
            applyDeliverableQuestNode(deliverableQuest.getNode(), deliverableQuest);
        });
    }

    private <T extends Quest> void applyDeliverableQuestNode(DialogNode node, DeliverableQuest<T> quest) {
        DialogTree tree;
        Optional<Entity> entity = EntityUtils.getEntity(quest.getTarget());
        DialogNode response = addQuestRequirement(node, Requirements.completedQuest(quest));
        if (entity.isPresent()) {
            Optional<DialogTree> optionalTree = dialogService.getDialog(entity.get());
            if (optionalTree.isPresent()) {
                tree = optionalTree.get();
                DialogNode newRoot = createNewRoot(tree.getRoot(), response);
                tree.setRoot(newRoot);
                dialogService.registerDialog(tree);
                registerQuestCompleteListener(node, tree, quest);
            } else {
                AtherysQuests.getInstance().getLogger().warn("NPC for deliverable quest {} has no dialog!", quest.getId());
            }
        } else {
            AtherysQuests.getInstance().getLogger().warn("NPC for deliverable quest {} does not exist!", quest.getId());
        }
    }

    private DialogNode addQuestRequirement(DialogNode node, Requirement requirement) {
        List<Requirement> requirements = Lists.newArrayList(node.getRequirements());
        requirements.add(requirement);
        return DialogNode.builder(node)
                .requirements(requirements.toArray(new Requirement[0])).build();
    }

    private DialogNode createNewRoot(DialogNode oldRoot, DialogNode newResponse) {
        List<DialogNode> nodes = Lists.newArrayList(oldRoot.getResponses());
        nodes.add(newResponse);
        return DialogNode.builder(oldRoot)
                .responses(nodes.toArray(new DialogNode[0])).build();
    }

    private <T extends Quest> void registerQuestCompleteListener(DialogNode node, DialogTree tree, Quest<T> quest) {
        EventListener<DialogProceedEvent> listener = event -> {
            if (event.getNode().getId() == node.getId() && event.getDialog().getTreeId().equals(tree.getId())) {
                Player player = event.getDialog().getCachedPlayer();
                AtherysQuests.getInstance().getQuesterFacade().turnInQuest(player, quest);
            }
        };
        Sponge.getEventManager().registerListener(AtherysQuests.getInstance(), DialogProceedEvent.class, listener);
    }
}

