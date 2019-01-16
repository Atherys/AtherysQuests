package com.atherys.quests.command.quest;

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

import javax.annotation.Nonnull;
import java.util.Optional;

@Aliases("log")
@Description("Displays log of current quests.")
@Permission("atherysquests.quest.log")
public class QuestLogCommand implements CommandExecutor {

    @Override
    @Nonnull
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        if (!(src instanceof Player)) return CommandResult.empty();

        AtherysQuests.getInstance().getQuesterFacade().showQuestLog((Player) src);

        return CommandResult.success();
    }
}
