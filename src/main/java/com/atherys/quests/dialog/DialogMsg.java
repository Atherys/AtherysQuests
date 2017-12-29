package com.atherys.quests.dialog;

import com.atherys.quests.dialog.tree.DialogNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

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

