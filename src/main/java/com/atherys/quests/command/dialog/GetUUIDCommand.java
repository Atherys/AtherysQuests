package com.atherys.quests.command.dialog;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.extent.EntityUniverse;

@Aliases("getUUID")
@Description("Get an entity's UUID, for scripting.")
@Permission("atherysquests.admin.quest.uuid")
public class GetUUIDCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) return CommandResult.empty();

        Player player = (Player) src;
        for (EntityUniverse.EntityHit entityHit : player.getWorld().getIntersectingEntities(player, 100)) {
            Entity next = entityHit.getEntity();
            if (next instanceof Player) continue;
            player.sendMessage(Text.of(next.getUniqueId()));
        }
        return CommandResult.success();
    }
}
