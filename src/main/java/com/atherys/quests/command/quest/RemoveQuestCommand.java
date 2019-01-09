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

import java.util.Optional;

@Permission("atherysquests.command.quest.remove")
@Description("Removes a quest from the player.")
@Aliases("remove")
public class RemoveQuestCommand implements ParameterizedCommand {
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
            GenericArguments.player(Text.of("player")),
            GenericArguments.string(Text.of("questId"))
        };
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> player = args.getOne("player");
        Optional<String> questId = args.getOne("questId");

        if (player.isPresent() && questId.isPresent()) {
            AtherysQuests.getInstance().getQuestService().getQuest(questId.get()).ifPresent(quest -> {
                AtherysQuests.getInstance().getQuesterService().getQuester(player.get()).removeQuest(quest);
            });
            return CommandResult.success();
        }

        return CommandResult.empty();
    }
}
