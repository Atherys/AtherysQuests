package com.atherys.quests.command.quest.attach;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.PlayerCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.quests.AtherysQuests;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;
import java.util.Optional;

@Permission("atherysquests.admin.quest.attach.item")
@Aliases("item")
@Description("Sets a quest to the item in hand.")
public class AttachQuestToItemCommand implements ParameterizedCommand, PlayerCommand {

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        Optional<String> questId = args.getOne("questId");

        AtherysQuests.getInstance().getQuestFacade().attachQuestToHeldItem(source, questId.orElse(null));

        return CommandResult.empty();
    }

    @Nonnull
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                GenericArguments.string(Text.of("questId"))
        };
    }

}
