package com.atherys.quests.quest.requirement;

import com.atherys.quests.managers.QuestManager;
import com.atherys.quests.quest.Quest;
import com.atherys.quests.quester.Quester;
import ninja.leaping.configurate.objectmapping.Setting;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextStyles;

import java.util.Optional;

public class QuestRequirement implements Requirement {

    @Setting
    private String questId;

    private QuestRequirement() {}

    public QuestRequirement( String questId ) {
        this.questId = questId;
    }

    public QuestRequirement( Quest quest ) { this.questId = quest.getId(); }

    @Override
    public Text toText() {
        Optional<Quest> quest = QuestManager.getInstance().getQuest(questId);
        if ( quest.isPresent() ) {
            return Text.of( "You have to have completed the ", TextStyles.ITALIC, TextStyles.BOLD, quest.get().getName(), TextStyles.RESET, " quest." );
        } else {
            return Text.of( "Uh oh. According to this, you have to have completed a quest which isn't registered. Please report this." );
        }
    }

    @Override
    public boolean check(Quester quester) {
        return quester.hasCompleted ( questId );
    }

    @Override
    public Requirement copy() {
        return new QuestRequirement( questId );
    }
}
