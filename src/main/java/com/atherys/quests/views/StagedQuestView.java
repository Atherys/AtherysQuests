package com.atherys.quests.views;

import com.atherys.quests.quest.StagedQuest;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class StagedQuestView implements QuestView<StagedQuest> {

    private final StagedQuest quest;

    public StagedQuestView ( StagedQuest stagedQuest ) {
        this.quest = stagedQuest;
    }

    @Override
    public Text getFormattedRequirements () {
        return null;
    }

    @Override
    public Text getFormattedObjectives () {
        return null;
    }

    @Override
    public Text getFormattedRewards () {
        return null;
    }

    @Override
    public void show ( Player player ) {

    }

}
