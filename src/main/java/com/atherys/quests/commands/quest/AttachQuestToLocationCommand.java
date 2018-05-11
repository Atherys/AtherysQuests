package com.atherys.quests.commands.quest;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.quests.managers.LocationManager;
import com.atherys.quests.util.QuestMsg;
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

@Aliases("tolocation")
@Description("Attaches a quest to a location.")
public class AttachQuestToLocationCommand implements ParameterizedCommand {

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        if(!(src instanceof Player)) return CommandResult.empty();

        Optional<Player> player = ((Player) src).getPlayer();
        Optional<Double> radius = args.getOne("radius");
        Optional<String> questId = args.getOne("questId");

        if(player.isPresent() && radius.isPresent() && questId.isPresent()){
            if(LocationManager.getInstance().addQuestLocation(player.get().getLocation(), questId.get(), radius.get())){
                QuestMsg.info(player.get(), "Quest set to location successfully.");
            } else {
                QuestMsg.error(player.get(), "Quest not set. The location overlaps with another or the quest does not exist");
            }
            return CommandResult.success();
        } else return CommandResult.empty();
    }

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                GenericArguments.string(Text.of("questId")),
                GenericArguments.doubleNum(Text.of("radius"))
        };
    }
}
