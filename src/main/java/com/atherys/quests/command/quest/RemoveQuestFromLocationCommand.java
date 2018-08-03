package com.atherys.quests.command.quest;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.util.QuestMsg;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class RemoveQuestFromLocationCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) return CommandResult.empty();

        Player player = (Player) src;

        if (AtherysQuests.getQuestCommandService().isAttachingQuest(player)) {
            QuestMsg.error(player, "You're currently attaching a quest.");
            return CommandResult.empty();
        } else if (AtherysQuests.getQuestCommandService().isRemovingQuest(player)){
            QuestMsg.info(player, "You're already removing a quest.");
            return CommandResult.success();
        } else {
            AtherysQuests.getQuestCommandService().startQuestRemoval(player);
            QuestMsg.info(player, "Right click to remove a quest from the location/block.");
            return CommandResult.success();
        }
    }
}
