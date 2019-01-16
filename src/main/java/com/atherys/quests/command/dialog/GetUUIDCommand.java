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
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.extent.EntityUniverse;

@Aliases("getuuid")
@Description("Get an entity's UUID, for scripting.")
@Permission("atherysquests.admin.dialog.getuuid")
public class GetUUIDCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) return CommandResult.empty();

        AtherysQuests.getInstance().getDialogFacade().getFacingEntityUuid((Player) src);

        return CommandResult.success();
    }
}
