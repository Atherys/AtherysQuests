package com.atherys.quests.command.dialog;

import com.atherys.core.command.PlayerCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.quests.AtherysQuests;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases("detach")
@Description("Removes a dialog from an entity.")
@Permission("atherysquests.admin.dialog.detach")
public class DetachDialogCommand implements PlayerCommand {

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        AtherysQuests.getInstance().getDialogFacade().startRemovingDialogFromFacingEntity(source);

        return CommandResult.success();
    }
}
