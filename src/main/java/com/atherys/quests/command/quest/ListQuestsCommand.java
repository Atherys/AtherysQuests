package com.atherys.quests.command.quest;

import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.quests.AtherysQuests;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import javax.annotation.Nonnull;

@Aliases("list")
@Permission("atherysquests.admin.quest.list")
@Description("Lists all currently loaded quests.")
public class ListQuestsCommand implements CommandExecutor {
    @Nonnull
    @Override
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        AtherysQuests.getInstance().getQuestFacade().listQuests(src);
        return CommandResult.success();
    }
}
