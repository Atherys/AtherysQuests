package com.atherys.quests.commands.dialog;

import com.atherys.quests.managers.DialogManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.extent.EntityUniverse;

import java.util.Optional;

public class DialogCommand implements CommandExecutor {

    @Override
    public CommandResult execute( CommandSource src, CommandContext args ) throws CommandException {
        if( !(src instanceof Player) ) return CommandResult.empty();

        return CommandResult.empty();
    }

    public CommandSpec getCommandSpec(){
        return CommandSpec.builder()
                .executor( this )
                .child( new AttachDialogCommand().getCommandSpec() )
                .build();
    }
}
