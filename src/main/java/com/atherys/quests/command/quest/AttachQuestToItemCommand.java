package com.atherys.quests.command.quest;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.data.QuestData;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;
import java.util.Optional;

@Permission("atherysquests.admin.quest.attach.item")
@Aliases("item")
@Description("Sets a quest to the item in hand.")
public class AttachQuestToItemCommand implements ParameterizedCommand {

    @Nonnull
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                GenericArguments.string(Text.of("questId"))
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        if(!(src instanceof Player)) return CommandResult.empty();

        Optional<String> questId = args.getOne("questId");

        AtherysQuests.getInstance().getQuestFacade().attachQuestToHeldItem((Player) src, questId.orElse(null));

        return CommandResult.empty();
    }
}
