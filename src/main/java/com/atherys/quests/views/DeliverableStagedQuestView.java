package com.atherys.quests.views;

import com.atherys.quests.api.quest.DeliverableQuest;
import com.atherys.quests.quest.DeliverableStagedQuest;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class DeliverableStagedQuestView extends StagedQuestView {
    public DeliverableStagedQuestView(DeliverableStagedQuest quest) {
        super(quest);
    }

    @Override
    public Text getCompletion(Player player) {
        return Text.of("You've completed this quest! Return it to ", ((DeliverableQuest) quest).getTargetName(), " to complete it.");
    }
}
