package com.atherys.quests.quest.requirement;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.services.QuestService;
import com.atherys.quests.quester.Quester;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextStyles;

import java.util.Optional;

/**
 * A requirement for checking whether or not the player has completed another quest.
 */
public class QuestRequirement implements Requirement {

    @Expose
    private String questId;

    QuestRequirement(String questId) {
        this.questId = questId;
    }

    QuestRequirement(Quest quest) {
        this.questId = quest.getId();
    }

    @Override
    public Text toText() {
        Optional<Quest> quest = QuestService.getInstance().getQuest(questId);
        if (quest.isPresent()) {
            return Text.of("You have to have completed the quest ", TextStyles.ITALIC, TextStyles.BOLD, quest.get().getName(), TextStyles.RESET);
        } else {
            return Text.of("Uh oh. According to this, you have to have completed a quest which isn't registered. Please report this.");
        }
    }

    @Override
    public boolean check(Quester quester) {
        return quester.hasCompleted(questId);
    }

    @Override
    public Requirement copy() {
        return new QuestRequirement(questId);
    }
}
