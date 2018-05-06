package com.atherys.quests.commands.quest;

import com.atherys.quests.managers.QuesterManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;
import java.util.Optional;

public class QuestLogCommand implements CommandExecutor {

    @Override
    @Nonnull
    public CommandResult execute( @Nonnull CommandSource src, @Nonnull CommandContext args ) throws CommandException {
        if ( !(src instanceof Player ) ) return CommandResult.empty();

        Optional<Player> player = ( (Player) src ).getPlayer();
        player.ifPresent((p -> QuesterManager.getInstance().getQuester( p ).getLog().show( p )));
        return CommandResult.success();
    }

    public CommandSpec getCommandSpec() {
        return CommandSpec.builder()
                .executor( this )
                .build();
    }
}
