package com.atherys.quests.quest.requirement;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quest.StagedQuest;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class ReachedStageRequirement implements Requirement {
    @Expose
    private final String questId;

    @Expose
    private final int stage;

    ReachedStageRequirement(String questId, int stage) {
        this.questId = questId;
        this.stage = stage;
    }

    @Override
    public boolean check(Quester quester) {
        Optional<Quest> quest = AtherysQuests.getInstance().getQuesterService().getQuesterQuest(quester, questId);
        return quest.map(q -> {
            if (q instanceof StagedQuest) {
                return ((StagedQuest) q).getCurrentIndex() >= stage;
            }
            return false;
        }).orElse(false);
    }

    @Override
    public Requirement copy() {
        return this;
    }

    @Override
    public Text toText() {
        return null;
    }
}
