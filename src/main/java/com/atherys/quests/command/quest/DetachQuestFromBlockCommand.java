package com.atherys.quests.command.quest;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.quests.AtherysQuests;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;

@Aliases("block")
@Description("Removes quest from block.")
@Permission("atherysquests.admin.quest.detach.block")
public class DetachQuestFromBlockCommand implements ParameterizedCommand {
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[0];
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        AtherysQuests.getInstance().getQuestFacade().detachQuestFromBlock();

        return CommandResult.empty();
    }
}
