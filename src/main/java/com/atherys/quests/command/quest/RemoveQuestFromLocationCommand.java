package com.atherys.quests.command.quest;

import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.util.QuestMsg;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

@Permission("atherysquests.admin.quest.remove.location")
@Aliases("location")
@Description("Remove quest from block or location.")
public class RemoveQuestFromLocationCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) return CommandResult.empty();

        Player player = (Player) src;

        if (AtherysQuests.getQuestAttachmentService().isAttaching(player)) {
            QuestMsg.error(player, "You're currently attaching a quest.");
            return CommandResult.empty();
        } else if (AtherysQuests.getQuestAttachmentService().isRemoving(player)){
            QuestMsg.info(player, "You're already removing a quest.");
            return CommandResult.success();
        } else {
            AtherysQuests.getQuestAttachmentService().startRemoval(player);
            QuestMsg.info(player, "Right click to remove a quest from the location/block.");
            return CommandResult.success();
        }
    }
}
