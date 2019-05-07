package com.atherys.quests.command.quest.attach;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.PlayerCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.quests.AtherysQuests;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;
import java.util.Optional;

@Permission("atherysquests.admin.quest.attach.block")
@Aliases("block")
@Description("Attaches a quest to a block.")
public class AttachQuestToBlockCommand implements ParameterizedCommand, PlayerCommand {

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        Optional<Double> radius = args.getOne("radius");
        Optional<String> questId = args.getOne("questId");

        AtherysQuests.getInstance().getQuestFacade().attachQuestToBlock(source, radius.get(), questId.get());

        return CommandResult.success();
    }

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                GenericArguments.string(Text.of("questId")),
                GenericArguments.doubleNum(Text.of("radius"))
        };
    }
}
