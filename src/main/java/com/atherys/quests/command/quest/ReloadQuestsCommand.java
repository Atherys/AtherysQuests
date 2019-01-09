package com.atherys.quests.command.quest;

import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.quests.AtherysQuests;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

@Aliases("reload")
@Description("Reloads script-created quests")
@Permission("atherysquests.quest.admin.reload")
public class ReloadQuestsCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        try {
            AtherysQuests.getInstance().getQuestScriptService().reloadScripts();
        } catch (Throwable e) {
            e.printStackTrace();
            src.sendMessage(Text.builder()
                    .append(Text.of(TextColors.DARK_RED, "Reloading quest script caused following error: ", Text.NEW_LINE))
                    .append(Text.of(TextColors.RED, e.getMessage()), Text.NEW_LINE)
                    .append(Text.of(TextColors.DARK_RED, "See console for stacktrace."))
                    .build()
            );
        }
        return CommandResult.success();
    }
}
