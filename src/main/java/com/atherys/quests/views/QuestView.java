package com.atherys.quests.views;

import com.atherys.core.views.View;
import com.atherys.quests.api.quest.Quest;
import org.spongepowered.api.text.Text;

public interface QuestView<T extends Quest> extends View<T> {

    Text getFormattedRequirements();

    Text getFormattedObjectives();

    Text getFormattedRewards();

}
