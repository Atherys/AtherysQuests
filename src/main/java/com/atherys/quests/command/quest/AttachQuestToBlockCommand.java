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

import java.util.HashMap;
import java.util.Optional;

@Permission("atherysquests.admin.quest.toblock")
@Aliases("toblock")
@Description("Attaches a quest to a block.")
public class AttachQuestToBlockCommand implements ParameterizedCommand {
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                GenericArguments.string(Text.of("questId")),
                GenericArguments.doubleNum(Text.of("radius"))
        };
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player)) return CommandResult.empty();

        Optional<String> questId = args.getOne("questId");
        Optional<Double> radius = args.getOne("radius");

        if (questId.isPresent() && radius.isPresent()){
            AtherysQuests.getQuestService().getQuest(questId.get()).ifPresent( quest -> {
                AtherysQuests.getQuestAdminService().startQuestSet((Player)src, questId.get(), radius.get());
            });
        }
        return CommandResult.success();
    }
}
