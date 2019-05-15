package com.atherys.quests.api.quest;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.QuestKeys;
import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogTree;
import com.atherys.quests.event.dialog.DialogProceedEvent;
import com.atherys.quests.util.EntityUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.text.Text;

import java.util.Optional;
import java.util.UUID;

public interface DeliverableQuest<T extends Quest> {
    UUID getTarget();

    Text getTargetName();

    default void applyNode(DialogNode node) {
        DialogTree tree;
        Optional<Entity> entity = EntityUtils.getEntity(getTarget());
        if (entity.isPresent()) {
            Optional<DialogTree> optionalTree = AtherysQuests.getInstance().getDialogService().getDialog(entity.get());
            if (optionalTree.isPresent()) {
                tree = optionalTree.get();
                DialogNode root = tree.getRoot();
                root.addResponse(node);
            } else {
                String dialogId = getQuest().getId() + "-generated";
                tree = DialogTree.builder(getQuest().getId() + "-generated")
                        .root(node).build();
                AtherysQuests.getInstance().getDialogService().registerDialog(tree);
                entity.get().offer(QuestKeys.DIALOG, dialogId);
            }

            EventListener<DialogProceedEvent> listener = event -> {
                if (event.getNode().getId() == node.getId() && event.getDialog().getTreeId().equals(tree.getId())) {
                    Player player = event.getDialog().getCachedPlayer();
                    AtherysQuests.getInstance().getQuesterFacade().turnInQuest(player, getQuest());
                }
            };
            Sponge.getEventManager().registerListeners(AtherysQuests.getInstance(), listener);
        }

    }

    Quest<T> getQuest();
}
