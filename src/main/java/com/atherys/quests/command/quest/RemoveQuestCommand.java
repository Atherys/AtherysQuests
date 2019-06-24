package com.atherys.quests.command.quest;

import com.atherys.core.command.ParameterizedCommand;
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

@Permission("atherysquests.admin.quest.remove")
@Description("Removes a quest from the player.")
@Aliases("remove")
public class RemoveQuestCommand implements ParameterizedCommand {
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                GenericArguments.flags().flag("f").buildWith(GenericArguments.none()),
                GenericArguments.player(Text.of("player")),
                GenericArguments.string(Text.of("questId"))
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> player = args.getOne("player");
        Optional<String> questId = args.getOne("questId");

        AtherysQuests.getInstance().getQuesterFacade().removeQuestFromPlayer(
                src, player.get(), questId.get(), args.hasAny("f")
        );

        return CommandResult.success();
    }
}
