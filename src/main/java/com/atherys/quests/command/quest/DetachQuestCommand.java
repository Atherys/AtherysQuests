package com.atherys.quests.command.quest;

import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Children;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

@Aliases("detach")
@Description("Base command for removing quests.")
@Children({
        DetachQuestFromItemCommand.class,
        DetachQuestFromLocationCommand.class,
        DetachQuestFromBlockCommand.class
})
public class DetachQuestCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) return CommandResult.empty();
        return CommandResult.empty();
    }
}
