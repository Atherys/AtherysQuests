package com.atherys.quests.views;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.service.QuestMessagingService;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextStyles;

public interface QuestView<T extends Quest> {

    default Text getFormattedRequirements() {
        Text.Builder reqText = Text.builder();
        reqText.append(Text.of(QuestMessagingService.MSG_PREFIX, " Quest Requirements: "));
        getQuest().getRequirements().forEach(requirement -> reqText.append(Text.NEW_LINE, Text.of(QuestMessagingService.MSG_PREFIX, " * ", requirement.toText())));
        return reqText.build();
    }

    default Text getFormattedObjectives() {
        Text.Builder objectives = Text.builder();
        objectives.append(Text.of("Objectives:\n"));
        getQuest().getObjectives().forEach(objective -> {
            objectives.append(Text.of(!objective.isComplete() ? TextStyles.NONE : TextStyles.STRIKETHROUGH, objective.toText(), TextStyles.RESET, Text.NEW_LINE));
        });

        return objectives.build();
    }

    default Text getFormattedRewards() {
        Text.Builder rewards = Text.builder();
        rewards.append(Text.of("Rewards:\n"));
        getQuest().getRewards().forEach(reward -> {
            rewards.append(Text.of(reward.toText(), Text.NEW_LINE));
        });
        return rewards.build();
    }

    BookView toBookView(Player player);

    default void show(Player player) {
        player.sendBookView(toBookView(player));
    }

     Quest<?> getQuest();
}
