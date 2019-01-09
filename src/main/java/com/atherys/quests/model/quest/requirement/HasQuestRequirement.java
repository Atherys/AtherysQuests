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

public class HasQuestRequirement implements Requirement {

    @Expose
    private String questId;

    HasQuestRequirement(String questId) {
        this.questId = questId;
    }

    HasQuestRequirement(Quest quest) {
        this.questId = quest.getId();
    }

    @Override
    public Text toText() {
        Optional<Quest> quest = getQuest();
        return quest
                .map(quest1 -> Text.of("You have to have completed the completedQuest ", TextStyles.ITALIC, TextStyles.BOLD, quest1.getName(), TextStyles.RESET))
                .orElseGet(() -> Text.of("Uh oh. According to this, you have to have completed a quest which isn't registered. This is likely an error, please report this."));
    }

    @Override
    public boolean check(Quester quester) {
        return getQuest().map(quester::hasQuest).orElse(false);
    }

    @Override
    public Requirement copy() {
        return new QuestCompleteRequirement(questId);
    }

    public Optional<Quest> getQuest() {
        return AtherysQuests.getQuestService().getQuest(questId);
    }
}
