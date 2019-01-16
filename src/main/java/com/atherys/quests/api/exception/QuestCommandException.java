package com.atherys.quests.api.exception;

import com.atherys.quests.service.QuestMessagingService;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class QuestCommandException extends CommandException {
    public QuestCommandException(Text message) {
        super(Text.of(QuestMessagingService.MSG_PREFIX, " ", TextColors.RED, message));
    }

    public QuestCommandException(String message) {
        this(Text.of(message));
    }
}
