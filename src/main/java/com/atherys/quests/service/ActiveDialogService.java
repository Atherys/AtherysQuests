package com.atherys.quests.service;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.dialog.Dialog;
import com.atherys.quests.dialog.DialogMsg;
import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogTree;
import com.atherys.quests.event.dialog.DialogEndEvent;
import com.atherys.quests.event.dialog.DialogProceedEvent;
import com.atherys.quests.event.dialog.DialogStartEvent;
import com.atherys.quests.views.TakeQuestView;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.spongepowered.api.text.format.TextColors.*;
import static org.spongepowered.api.text.format.TextStyles.BOLD;
import static org.spongepowered.api.text.format.TextStyles.ITALIC;

@Singleton
public class ActiveDialogService {
    @Inject
    QuesterService questerService;

    /**
     * Begins a dialog between a {@link Player} and an {@link Entity}.
     */
    public Optional<Dialog> dialogBetween(Player player, Entity entity, DialogTree dialogTree) {
        Quester simpleQuester = AtherysQuests.getInstance().getQuesterService().getQuester(player);

        Dialog dialog = new Dialog(simpleQuester, entity, dialogTree);
        proceedDialog(dialog, dialog.getLastNode(), player);
        return Optional.of(dialog);
    }

    private void proceedDialog(Dialog dialog, DialogNode node, Player player) {

        // update the cached player
        dialog.updatePlayer(player);

        if (node.getId() == 0) {
            Sponge.getEventManager().post(new DialogStartEvent(node, dialog));
            Sponge.getEventManager().post(new DialogProceedEvent(node, dialog));
        } else if (node.getResponses().isEmpty()) {
            Sponge.getEventManager().post(new DialogEndEvent(node, dialog));
            Sponge.getEventManager().post(new DialogProceedEvent(node, dialog));
        } else {
            Sponge.getEventManager().post(new DialogProceedEvent(node, dialog));
        }

        // If the node provided is not the current node or a child of the current node, return.
        if (dialog.getLastNode() == node || dialog.getLastNode().getResponses().contains(node)) {

            if (!questerMeetsRequirements(node, dialog.getQuester())) {
                DialogMsg.error(player, "You do not meet the requirements for this response.");
                return;
            }

            dialog.setLastNode(node);

            showDialog(dialog, player);

            node.getQuest().ifPresent(quest -> new TakeQuestView(quest).show(player));

            node.getCompletesQuest().ifPresent(quest -> {
                questerService.turnInQuest(dialog.getQuester(), quest);
            });

            if (node.getResponses().isEmpty()) {
                AtherysQuests.getInstance().getDialogService().removePlayerDialog(player);
            }
        }
    }

    private boolean questerMeetsRequirements(DialogNode node, Quester quester) {
        if (node.getRequirements() == null) return true;
        for (Requirement requirement : node.getRequirements()) {
            if (!requirement.check(quester)) return false;
        }
        return true;
    }

    private void showDialog(Dialog dialog, Player player) {
        DialogNode node = dialog.getLastNode();
        Entity npc = dialog.getNPC();

        player.sendMessage(DialogMsg.DIALOG_START_DECORATION);

        // Player Text
        if (node.getPlayerText() != null) {
            playerTextTask(player, node.getPlayerText());
        }

        // NPC Text
        if (node.getNPCText() != null) {
            npcTextTask(npc, node.getNPCText(), player);
        }

        // Possible Responses
        if (node.getResponses().size() >= 1) {
            responesTextTask(dialog, node, player);
        } else {
            player.sendMessage(DialogMsg.DIALOG_END_DECORATION);
        }
    }

    private void playerTextTask(Player player, Text text) {
        String taskName = "atherysquests-dialog-player-" + player.getName();
        if (Sponge.getScheduler().getTasksByName(taskName).isEmpty()) {
            Task.builder().name(taskName).delay(1, TimeUnit.SECONDS)
                    .execute(() -> player.sendMessage(Text.of(AQUA, BOLD, player.getName(), TextStyles.RESET, TextColors.RESET, ": ", text)))
                    .submit(AtherysQuests.getInstance());
        }
    }

    private void npcTextTask(Entity npc, List<Text> text, Player player) {
        int i = 0;
        for (Text sentence : text) {
            Task.builder()
                    .name("atherysquests-dialog-npc-" + i + "-" + player.getName())
                    .delay(AtherysQuests.getConfig().DIALOG_MESSAGE_DELAY * i + AtherysQuests.getConfig().DIALOG_MESSAGE_DELAY, TimeUnit.SECONDS)
                    .execute(() -> {
                        player.sendMessage(
                                Text.of(AQUA, BOLD, npc.get(Keys.DISPLAY_NAME).orElse(Text.of(StringUtils.capitalize(npc.getType().getName()))), TextStyles.RESET, TextColors.RESET, ": ", sentence)
                        );
                        player.playSound(SoundTypes.ENTITY_VILLAGER_AMBIENT, npc.getLocation().getPosition(), 0.2d);
                    })
                    .submit(AtherysQuests.getInstance());
            i++;
        }
    }

    private void responesTextTask(Dialog dialog, DialogNode node, Player player) {
        long responsesDelay = AtherysQuests.getConfig().DIALOG_MESSAGE_DELAY * node.getNPCText().size() + AtherysQuests.getConfig().DIALOG_MESSAGE_DELAY;
        Quester quester = questerService.getQuester(player);

        Runnable runnable = () -> {
            player.sendMessage(DialogMsg.DIALOG_REPLIES_DECORATION);

            int i = 1;
            for (DialogNode response : node.getResponses()) {
                // If the response is hidden, check if they meet requirements before showing
                if (!response.isHidden() || questerMeetsRequirements(response, quester)) {
                    player.sendMessage(buildResponseText(dialog, response, i));
                    i++;
                }
            }
        };

        Task.builder()
                .name("atherysquests-dialog-player-responses-" + player.getName())
                .delay(responsesDelay, TimeUnit.SECONDS)
                .execute(runnable)
                .submit(AtherysQuests.getInstance());
    }

    private Text buildResponseText(Dialog dialog, DialogNode response, int i) {
        Text.Builder responseText = Text.builder()
                .append(Text.of(DARK_AQUA, "[", WHITE, BOLD, i, TextStyles.RESET, DARK_AQUA, "]"))
                .append(Text.of(AQUA, BOLD, "You", TextStyles.RESET, TextColors.RESET, ": ", response.getPlayerText()))
                .onClick(TextActions.executeCallback(src -> proceedDialog(dialog, response, (Player) src)))
                .onHover(TextActions.showText(Text.of("Say ", ITALIC, response.getPlayerText())));

        response.getQuest().ifPresent(quest -> {
            responseText.append(
                    Text.builder()
                            .append(Text.of(DARK_GREEN, BOLD, " { Starts Quest: ", GREEN, TextStyles.RESET, quest.getName(), BOLD, DARK_GREEN, " }"))
                            .onHover(TextActions.showText(quest.createView().getFormattedRequirements()))
                            .build()
            );
        });
        return responseText.build();
    }
}
