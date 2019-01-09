package com.atherys.quests.service;

import com.atherys.quests.api.quester.Quester;
import com.google.inject.Inject;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class QuestMessagingService {

    public static final Text MSG_PREFIX = Text.of(TextColors.DARK_GREEN, "[", TextStyles.BOLD, TextColors.GOLD, "Quest", TextStyles.RESET, TextColors.DARK_GREEN, "]", TextColors.RESET);

    @Inject
    QuesterService questerService;

    public void noformat(Quester quester, Object... msg) {
        Player player = questerService.getCachedPlayer(quester);
        if (player != null) player.sendMessage(Text.of(msg));
    }

    public void error(Quester quester, Object... msg) {
        noformat(quester, Text.of(MSG_PREFIX, " ", TextColors.RED, Text.of(msg)));
    }

    public void error(Player player, Object... msg) {
        player.sendMessage(Text.of(MSG_PREFIX, TextColors.RED, " ", Text.of(msg)));
    }

    public void info(Quester quester, Object... msg) {
        noformat(quester, Text.of(MSG_PREFIX, TextColors.GREEN, " ", Text.of(msg)));
    }

    public void info(Player player, Object... msg) {
        player.sendMessage(Text.of(MSG_PREFIX, TextColors.GREEN, " ", Text.of(msg)));
    }

}
