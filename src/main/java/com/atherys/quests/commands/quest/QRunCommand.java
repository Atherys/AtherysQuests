package com.atherys.quests.commands.quest;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.quests.script.js.QuestsLib;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.List;

@Aliases("qrun")
@Description("Runs a script")
@Permission("atherysquests.admin.qrun")
public class QRunCommand implements ParameterizedCommand {

    private static final Text ERROR_PREFIX = Text.of(TextColors.DARK_RED, "[", TextColors.RED, "ERROR", TextColors.DARK_RED, "]", TextColors.RED);

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                GenericArguments.remainingJoinedStrings(Text.of("javascript"))
        };
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            String script = args.<String>getOne("javascript").orElse("");

            if (script.isEmpty()) return CommandResult.empty();

            else {
                try {

                    QuestsLib.getInstance().getEngine().eval(script);

                } catch (Exception e) {

                    Text.Builder errorMsg = Text.builder();

                    errorMsg.append(error(e.getMessage()));
                    for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                        errorMsg.append(error(stackTraceElement.toString()));
                    }

                    Text.Builder error = Text.builder();
                    error.append(Text.of(ERROR_PREFIX, "An Error occured while executring your script. Hover over this message to read the stacktrace."));
                    error.onHover(TextActions.showText(Text.join(errorMsg.build())));

                    src.sendMessage(error.build());

                }
            }
        }
        return CommandResult.empty();
    }

    private static Text error(String msg) {
        return Text.of(TextColors.RED, msg, Text.NEW_LINE);
    }
}
