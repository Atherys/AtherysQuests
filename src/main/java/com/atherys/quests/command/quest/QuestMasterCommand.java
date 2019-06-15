package com.atherys.quests.command.quest;

import com.atherys.core.command.PlayerCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Children;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.HelpCommand;
import com.atherys.quests.command.quest.attach.AttachQuestCommand;
import com.atherys.quests.command.quest.detach.DetachQuestCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases("quest")
@Description("Base quest command.")
@Children({
        QuestLogCommand.class,
        AttachQuestCommand.class,
        DetachQuestCommand.class,
        ReloadQuestsCommand.class,
        GiveQuestCommand.class,
        ListQuestsCommand.class,
        RemoveQuestCommand.class
})
@HelpCommand(title = "Quest Help")
public class QuestMasterCommand implements PlayerCommand {

    @Override
    @Nonnull
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        return CommandResult.success();
    }
}
