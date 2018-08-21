package com.atherys.quests.command.quest;

import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.quests.data.QuestData;
import com.atherys.quests.util.QuestMsg;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

@Permission("atherysquests.admin.quest.remove.item")
@Aliases("item")
@Description("Removes quest from item.")
public class RemoveQuestFromItemCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) return CommandResult.empty();

        Player player = (Player) src;

        Optional<ItemStack> itemStack = player.getItemInHand(HandTypes.MAIN_HAND);
        itemStack.ifPresent(item ->{
            item.remove(QuestData.class);
            QuestMsg.info(player, "Any quests have been removed from the item.");
        });
        return CommandResult.success();
    }
}
