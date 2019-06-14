package com.atherys.quests.quest.requirement;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.api.requirement.Requirement;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextStyles;

import java.util.Optional;

public class QuestCompleteRequirement implements Requirement {
    @Expose
    private String questId;

    QuestCompleteRequirement(String questId) {
        this.questId = questId;
    }

    QuestCompleteRequirement(Quest quest) {
        questId = quest.getId();
    }

    @Override
    public boolean check(Quester quester) {
        return AtherysQuests.getInstance().getQuesterService().questerHasCompletedQuest(quester, questId);
    }

    @Override
    public Requirement copy() {
        return new QuestCompleteRequirement(questId);
    }

    @Override
    public Text toText() {
        Optional<Quest> quest = AtherysQuests.getInstance().getQuestService().getQuest(questId);
        if (quest.isPresent()) {
            return Text.of("You have to have completed the Quest ",
                    TextStyles.ITALIC, TextStyles.BOLD, quest.get().getName(), TextStyles.RESET);
        } else {
            return Text.of("Uh oh. According to this, you have to have completed a quest which isn't registered. Please report this.");
        }
    }
}
