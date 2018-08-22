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


@Permission("atherysquests.admin.quest.cancel")
@Aliases("cancel")
@Description("Cancels the attachment or removal of a quest.")
public class CancelQuestCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) return CommandResult.empty();

        Player player = (Player) src;
        AtherysQuests.getQuestAttachmentService().endAttachment(player);
        AtherysQuests.getQuestAttachmentService().endRemoval(player);

        QuestMsg.info(player, "Quest attachment/removal cleared.");

        return CommandResult.empty();
    }
}
