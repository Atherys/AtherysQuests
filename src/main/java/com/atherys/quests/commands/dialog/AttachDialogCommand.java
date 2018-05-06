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

import javax.annotation.Nonnull;
import java.util.Optional;

public class AttachDialogCommand implements CommandExecutor {

    @Nonnull
    @Override
    public CommandResult execute( CommandSource src, CommandContext args ) throws CommandException {
        if ( !(src instanceof Player) ) return CommandResult.empty();

        Optional<Player> player = ( (Player) src).getPlayer();
        player.ifPresent(p -> {
            for ( EntityUniverse.EntityHit entityHit : p.getWorld().getIntersectingEntities( p, 100 ) ) {
                Entity next = entityHit.getEntity();
                if ( next instanceof Player ) continue;

                p.sendMessage( Text.of( DialogManager.getInstance().setDialog( entityHit.getEntity(), DialogManager.getInstance().getDialogFromId( args.<String>getOne( "dialogId" ).get() ).get() ) ) );
            }
        });
        return CommandResult.success();
    }

    public CommandSpec getCommandSpec(){
        return CommandSpec.builder()
                .executor( this )
                .build();
    }
}
