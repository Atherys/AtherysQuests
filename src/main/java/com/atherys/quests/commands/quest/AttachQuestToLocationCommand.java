package com.atherys.quests.commands.quest;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;


public class AttachQuestToLocationCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player)) return CommandResult.empty();
        return CommandResult.success();


    }

    public CommandSpec getCommandSpec() {
        return CommandSpec.builder()
                .executor(this)
                .arguments(GenericArguments.string(Text.of("dialogId")))
                .arguments(GenericArguments.doubleNum(Text.of("radius")))
                .build();
    }
}
