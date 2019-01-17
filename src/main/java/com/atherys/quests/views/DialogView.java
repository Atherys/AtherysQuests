package com.atherys.quests.views;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.dialog.Dialog;
import com.atherys.quests.dialog.DialogMsg;
import com.atherys.quests.dialog.tree.DialogNode;
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

import java.util.concurrent.TimeUnit;

public class DialogView {

    private final Dialog dialog;

    public DialogView(Dialog dialog) {
        this.dialog = dialog;
    }

    public void showChat(Player player) {
        DialogNode node = dialog.getLastNode();
        Entity npc = dialog.getNPC();

        player.sendMessage(DialogMsg.DIALOG_START_DECORATION);

        // Player Text
        if (node.getPlayerText() != null) {
            String taskName = "atherysquests-dialog-player-" + player.getName();
            if (Sponge.getScheduler().getTasksByName(taskName).isEmpty()) {
                Task.builder().name(taskName).delay(1, TimeUnit.SECONDS)
                        .execute(() -> player.sendMessage(Text.of(TextColors.AQUA, TextStyles.BOLD, player.getName(), TextStyles.RESET, TextColors.RESET, ": ", node.getPlayerText())))
                        .submit(AtherysQuests.getInstance());
            }
        }

        // NPC Text
        if (node.getNPCText() != null) {
            for (int i = 0; i < node.getNPCText().length; i++) {
                Text sentence = node.getNPCText()[i];
                Task.builder()
                        .name("atherysquests-dialog-npc-" + i + "-" + player.getName())
                        .delay(AtherysQuests.getConfig().DIALOG_MESSAGE_DELAY * i + AtherysQuests.getConfig().DIALOG_MESSAGE_DELAY, TimeUnit.SECONDS)
                        .execute(() -> {
                            player.sendMessage(
                                    Text.of(TextColors.AQUA, TextStyles.BOLD, npc.get(Keys.DISPLAY_NAME).orElse(Text.of(StringUtils.capitalize(npc.getType().getName()))), TextStyles.RESET, TextColors.RESET, ": ", sentence)
                            );
                            player.playSound(SoundTypes.ENTITY_VILLAGER_AMBIENT, npc.getLocation().getPosition(), 0.2d);
                        })
                        .submit(AtherysQuests.getInstance());
            }
        }

        // Possible Responses
        if (node.getResponses().size() >= 1) {

            long responsesDelay = AtherysQuests.getConfig().DIALOG_MESSAGE_DELAY * node.getNPCText().length + AtherysQuests.getConfig().DIALOG_MESSAGE_DELAY;

            Runnable runnable = () -> {
                player.sendMessage(DialogMsg.DIALOG_REPLIES_DECORATION);

                int i = 1;
                for (DialogNode response : node.getResponses()) {
                    Text.Builder nextMessage = Text.builder()
                            .append(Text.of(TextColors.DARK_AQUA, "[", TextColors.WHITE, TextStyles.BOLD, i, TextStyles.RESET, TextColors.DARK_AQUA, "]"))
                            .append(Text.of(TextColors.AQUA, TextStyles.BOLD, "You", TextStyles.RESET, TextColors.RESET, ": ", response.getPlayerText()))
                            .onClick(TextActions.executeCallback(src -> dialog.proceed((Player) src, response)))
                            .onHover(TextActions.showText(Text.of("Say ", TextStyles.ITALIC, response.getPlayerText())));

                    response.getQuest().ifPresent(quest -> {
                        nextMessage.append(
                                Text.builder()
                                        .append(Text.of(TextColors.DARK_GREEN, TextStyles.BOLD, " { Starts Quest: ", TextColors.GREEN, TextStyles.RESET, quest.getName(), TextStyles.BOLD, TextColors.DARK_GREEN, " }"))
                                        .onHover(TextActions.showText(quest.createView().getFormattedRequirements()))
                                        .build()
                        );
                    });

                    player.sendMessage(nextMessage.build());
                    i++;
                }
            };

            Task.builder()
                    .name("atherysquests-dialog-player-responses-" + player.getName())
                    .delay(responsesDelay, TimeUnit.SECONDS)
                    .execute(runnable)
                    .submit(AtherysQuests.getInstance());
        } else player.sendMessage(DialogMsg.DIALOG_END_DECORATION);
    }

    public void show(Player player) {
        showChat(player);
    }
}
