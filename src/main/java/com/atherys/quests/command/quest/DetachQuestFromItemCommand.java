package com.atherys.quests.command.quest;

import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.data.QuestData;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

@Aliases("item")
@Description("Removes quest from item.")
@Permission("atherysquests.admin.quest.detach.item")
public class DetachQuestFromItemCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) return CommandResult.empty();

        AtherysQuests.getInstance().getQuestFacade().detatchQuestFromHeldItem((Player) src);

        return CommandResult.success();
    }
}
