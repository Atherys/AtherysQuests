package com.atherys.quests.commands.dialog;

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

@Aliases("dialog")
@Description("Base dialog command.")
@Children({AttachDialogCommand.class,
        GetDialogCommand.class
})
public class DialogMasterCommand implements CommandExecutor {

    @Nonnull
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player)) return CommandResult.empty();

        return CommandResult.empty();
    }
}
