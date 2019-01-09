package com.atherys.quests.command.quest;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.QuestLocationType;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Permission("atherysquests.admin.quest.attach.location")
@Aliases("location")
@Description("Attaches a quest to a location.")
public class AttachQuestToLocationCommand implements ParameterizedCommand {

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        if (!(src instanceof Player)) return CommandResult.empty();

        Player player = (Player) src;
        AtherysQuests.getInstance().getLogger().info(player.toString());
        if (AtherysQuests.getInstance().getQuestAttachmentService().isRemoving(player)){
            AtherysQuests.getInstance().getQuestMessagingService().error(player, "You're currently removing a quest.");
            return CommandResult.empty();
        }

        Optional<Double> radius = args.getOne("radius");
        Optional<String> questId = args.getOne("questId");
        Optional<String> type = args.getOne("type");

        if (radius.isPresent() && questId.isPresent() && type.isPresent()) {
            if (AtherysQuests.getInstance().getQuestService().getQuest(questId.get()).isPresent()){
                AtherysQuests.getInstance().getQuestAttachmentService().startAttachment(player, questId.get(), radius.get(), QuestLocationType.valueOf(type.get()));
                AtherysQuests.getInstance().getQuestMessagingService().info(player, "Right click on a block to set the quest.");
                return CommandResult.success();
            } else {
                AtherysQuests.getInstance().getQuestMessagingService().error(player, "Quest ID invalid.");
                return CommandResult.empty();
            }
        }
        return CommandResult.empty();
    }

    private static Map<String, String> choices;
    static {
        choices = new HashMap<String, String>();
        choices.put("BLOCK", "BLOCK");
        choices.put("RADIUS", "RADIUS");
    }

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                GenericArguments.string(Text.of("questId")),
                GenericArguments.doubleNum(Text.of("radius")),
                GenericArguments.choices(Text.of("type"), choices)
        };
    }
}
