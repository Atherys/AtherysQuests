package com.atherys.quests.quest;

import com.atherys.quests.quester.Quester;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class QuestMsg {
    public static final Text MSG_PREFIX = Text.of( TextColors.DARK_GREEN, "[", TextStyles.BOLD, TextColors.GOLD, "Quest", TextStyles.RESET, TextColors.DARK_GREEN, "]", TextColors.RESET );

    public static void noformat( Quester quester, Text text ) {
        quester.getUser().ifPresent( user -> user.getPlayer().ifPresent( player -> player.sendMessage( text ) ) );
    }

    public static void error( Quester quester, Object... msg ) {
        noformat( quester, Text.of( MSG_PREFIX, " ", TextColors.RED, Text.of( msg ) ) );
    }

    public static void error( Player player, Object... msg ) {
        player.sendMessage( Text.of( MSG_PREFIX, TextColors.RED, Text.of( " ", msg ) ) );
    }

    public static void info( Quester quester, Object... msg ) {
        noformat( quester, Text.of( TextColors.GREEN, " ", msg ) );
    }
}
