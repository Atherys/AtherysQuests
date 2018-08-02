package com.atherys.quests.command.dialog;

import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.quests.dialog.tree.DialogTree;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.extent.EntityUniverse;

import javax.annotation.Nonnull;
import java.util.Optional;

@Aliases("get")
@Description("Returns the ID of dialog from an entity.")
public class GetDialogCommand implements CommandExecutor {

    @Nonnull
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) return CommandResult.empty();

        Optional<Player> player = ((Player) src).getPlayer();
        player.ifPresent(p -> {
            for (EntityUniverse.EntityHit entityHit : p.getWorld().getIntersectingEntities(p, 100)) {
                Entity next = entityHit.getEntity();
                if (next instanceof Player) continue;
                Optional<DialogTree> tree = DialogService.getInstance().getDialog(entityHit.getEntity());
                p.sendMessage(tree.map(dialogTree -> Text.of("Dialog ID: ", dialogTree.getId())).orElseGet(() -> Text.of("Dialog ID: none")));
            }
        });
        return CommandResult.success();
    }
}
