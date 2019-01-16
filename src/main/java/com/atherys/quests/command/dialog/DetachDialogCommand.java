package com.atherys.quests.command.dialog;

import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.quests.AtherysQuests;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

@Aliases("detach")
@Description("Removes a dialog from an entity.")
@Permission("atherysquests.admin.dialog.detach")
public class DetachDialogCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) return CommandResult.empty();

        AtherysQuests.getInstance().getDialogFacade().startRemovingDialogFromFacingEntity((Player) src);

        return CommandResult.success();
    }
}
