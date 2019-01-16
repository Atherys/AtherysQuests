package com.atherys.quests.api.exception;

public class QuestCommandExceptions {

    public static QuestCommandException notImplemented() {
        return new QuestCommandException("This command has not been implemented yet.");
    }

    public static QuestCommandException invalidQuestId() {
        return new QuestCommandException("Invalid quest id.");
    }
}
