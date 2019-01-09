package com.atherys.quests.model.quest.requirement;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.model.SimpleQuester;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextStyles;

import java.util.Optional;

/**
 * A requirement for checking whether or not the player has completed another completedQuest.
 */
public class QuestCompleteRequirement implements Requirement {

    @Expose
    private String questId;

    QuestCompleteRequirement(String questId) {
        this.questId = questId;
    }

    QuestCompleteRequirement(Quest quest) {
        this.questId = quest.getId();
    }

    @Override
    public Text toText() {
        Optional<Quest> quest = AtherysQuests.getInstance().getQuestService().getQuest(questId);
        if (quest.isPresent()) {
            return Text.of("You have to have completed the completedQuest ", TextStyles.ITALIC, TextStyles.BOLD, quest.get().getName(), TextStyles.RESET);
        } else {
            return Text.of("Uh oh. According to this, you have to have completed a completedQuest which isn't registered. Please report this.");
        }
    }

    @Override
    public boolean check(Quester quester) {
        return AtherysQuests.getInstance().getQuestService().hasQuesterFinishedQuest(quester, questId);
    }

    @Override
    public Requirement copy() {
        return new QuestCompleteRequirement(questId);
    }
}
