package com.atherys.quests.dialog;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.dialog.tree.DialogNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.concurrent.TimeUnit;

/**
 * A utility class for formatting and sending {@link Dialog}-related messages to players.
 */
public class DialogMsg {

    public static final Text DIALOG_START_DECORATION = Text.of(TextColors.AQUA, "o════════════╡Dialog╞════════════o");
    public static final Text DIALOG_REPLIES_DECORATION = Text.of(TextColors.AQUA, "o═════════╡Possible Replies╞═════════o");
    public static final Text DIALOG_END_DECORATION = Text.of(TextColors.AQUA, "o═══════════╡End Dialog╞══════════o");

    /**
     * The prefix of all {@link Dialog}-related messages;
     */
    public static final Text MSG_PREFIX = Text.of( TextColors.DARK_AQUA, "[", TextStyles.BOLD, TextColors.AQUA, "Dialog", TextStyles.RESET, TextColors.DARK_AQUA, "]", TextColors.RESET );

    /**
     * Sends an info message to the given player.
     * @param player The player to whom the message will be sent.
     * @param msg The message. Will later be wrapped in a {@link Text} object.
     */
    public static void info (Player player, Object... msg ) {
        player.sendMessage( Text.of(MSG_PREFIX, TextColors.WHITE, " ", Text.of ( msg ) ) );
    }

    /**
     * Sends an error message to the given player.
     * @param player The player to whom the message will be sent.
     * @param msg The message. Will later be wrapped in a {@link Text} object.
     */
    public static void error ( Player player, Object... msg ) {
        player.sendMessage( Text.of(MSG_PREFIX, TextColors.RED, " ", Text.of ( msg ) ) );
    }

    public static void self ( Player player, Object... msg ) {
        String taskName = "atherysquest-dialog-player-text-" + player.getName();
        if ( Sponge.getScheduler().getTasksByName(taskName).isEmpty() ) {
            Task.builder().name(taskName).delay(1, TimeUnit.SECONDS)
                    .execute( () -> player.sendMessage( Text.of(TextColors.AQUA, TextStyles.BOLD, player.getName(), TextStyles.RESET, TextColors.RESET, ": ", msg ) ) )
                    .submit(AtherysQuests.getInstance());
        }
    }

    public static void npc (Player player, Entity npc, Text[] npcText ) {
        for ( int i = 0 ; i < npcText.length; i++ ) {
            Text sentence = npcText[i];
            Task.builder()
                    .name("atherysquests-dialog-npc-" + i + "-" + player.getName() )
                    .delay( AtherysQuests.getConfig().DIALOG_MESSAGE_DELAY * i + AtherysQuests.getConfig().DIALOG_MESSAGE_DELAY, TimeUnit.SECONDS )
                    .execute( () -> {
                        player.sendMessage(
                                Text.of( TextColors.AQUA, TextStyles.BOLD, npc.get(Keys.DISPLAY_NAME).orElse(Text.of("NPC")), TextStyles.RESET, TextColors.RESET, ": ", sentence )
                        );
                        player.playSound( SoundTypes.ENTITY_VILLAGER_AMBIENT, npc.getLocation().getPosition(), 0.2d );
                    })
                    .submit( AtherysQuests.getInstance() );
        }
    }

    /**
     * Sends an info message to the given user. Uses {@link User#isOnline()} to determine if the user is online first. If so, the message will be delivered, else not.
     * @param user The user to whom the message will be sent.
     * @param msg The message. Will later be wrapped in a {@link Text} object.
     */
    public static void info ( User user, Object... msg ) {
        if ( user.isOnline() && user.getPlayer().isPresent() ) info ( user.getPlayer().get(), msg );
    }

    /**
     * Sends an error message to the given user. Uses {@link User#isOnline()} to determine if the user is online first. If so, the message will be delivered, else not.
     * @param user The user to whom the message will be sent.
     * @param msg The message. Will later be wrapped in a {@link Text} object.
     */
    public static void error ( User user, Object... msg ) {
        if ( user.isOnline() && user.getPlayer().isPresent() ) error ( user.getPlayer().get(), msg );
    }

    public static void response(Player player, int i, DialogNode response) {

    }
}

