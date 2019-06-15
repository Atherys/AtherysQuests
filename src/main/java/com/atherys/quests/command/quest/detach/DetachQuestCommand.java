package com.atherys.quests.command.quest.detach;

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

@Aliases("detach")
@Description("Base command for removing quests.")
@Children({
        DetachQuestFromItemCommand.class,
        DetachQuestFromLocationCommand.class,
        DetachQuestFromBlockCommand.class
})
@HelpCommand(title = "Quest Detachment Help", prefix = "quest")
public class DetachQuestCommand implements PlayerCommand {

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        return CommandResult.success();
    }
}
