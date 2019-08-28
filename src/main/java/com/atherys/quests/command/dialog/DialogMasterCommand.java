package com.atherys.quests.command.dialog;

import com.atherys.core.command.PlayerCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Children;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.HelpCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases("dialog")
@Description("Base dialog command.")
@Children({
        AttachDialogCommand.class,
        DetachDialogCommand.class,
        GetDialogCommand.class,
        ListDialogsCommand.class,
        QuitDialogCommand.class,
        ReloadDialogsCommand.class
})
@HelpCommand(title = "Dialog Help")
public class DialogMasterCommand implements PlayerCommand {

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        return CommandResult.success();
    }
}
