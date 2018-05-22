package com.atherys.quests.commands.quest;

import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Children;
import com.atherys.core.command.annotation.Description;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases("quest")
@Description("Base quest command.")
@Children({QuestLogCommand.class,
           AttachQuestToLocationCommand.class})
public class QuestMasterCommand implements CommandExecutor {

    @Override
    @Nonnull
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        if(!(src instanceof Player)) return CommandResult.empty();
        return CommandResult.empty();
    }
}
