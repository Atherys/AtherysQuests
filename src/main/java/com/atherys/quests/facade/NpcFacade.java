package com.atherys.quests.facade;

import com.atherys.quests.QuestsConfig;
import com.atherys.quests.api.exception.QuestCommandException;
import com.atherys.quests.service.QuestMessagingService;
import com.atherys.quests.util.EntityUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import static org.spongepowered.api.text.format.TextColors.DARK_GREEN;
import static org.spongepowered.api.text.format.TextColors.GOLD;

@Singleton
public class NpcFacade {
    @Inject
    QuestsConfig config;

    @Inject
    QuestMessagingService questMsg;

    public void addNpc(String name, Player player) {
        EntityUtils.getNonPlayerFacingEntity(player, 5).ifPresent(entity -> {
            config.NPCS.put(name, entity.getUniqueId());
            questMsg.info(player, "Added NPC with name ", GOLD, name, DARK_GREEN, ".");
        });
    }

    public void removeNpc(String name, CommandSource source) throws CommandException {
        if (config.NPCS.remove(name) != null) {
            questMsg.info(source, "Removed NPC with name ", GOLD, name, DARK_GREEN, ".");
        } else {
            throw new QuestCommandException(Text.of("Could not find NPC with name ", name, "."));
        }
    }
}
