package com.atherys.quests.command.quest;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.data.QuestData;
import com.atherys.quests.util.QuestMsg;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;
import java.util.Optional;

@Permission("atherysquests.admin.quest.toitem")
@Aliases("item")
@Description("Sets a quest to the item in hand.")
public class AttachQuestToItemCommand implements ParameterizedCommand {

    @Nonnull
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                GenericArguments.string(Text.of("questId"))
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        if(!(src instanceof Player)) return CommandResult.empty();

        Optional<Player> player = ((Player) src).getPlayer();
        Optional<String> questId = args.getOne("questId");

        if(player.isPresent() && questId.isPresent()) {
            Optional<Quest> quest = AtherysQuests.getQuestService().getQuest(questId.get());
            Optional<ItemStack> itemStack = player.get().getItemInHand(HandTypes.MAIN_HAND);
            if(quest.isPresent() && itemStack.isPresent()) {
                ItemStack item = itemStack.get();
                boolean isQuestItem = item.get(QuestData.class).isPresent();
                item.offer(new QuestData(questId.get()));
                player.get().setItemInHand(HandTypes.MAIN_HAND, item);
                QuestMsg.info(player.get(), "Quest set to object successfully.");
                return CommandResult.success();
            } else {
                QuestMsg.error(player.get(), "The quest does not exist or you are not holding an item.");
                return CommandResult.empty();
            }
        }
        return CommandResult.empty();
    }
}
