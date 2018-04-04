package com.atherys.quests.views;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.quest.Stage;
import com.atherys.quests.quest.StagedQuest;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class StagedQuestView extends AnyQuestView<StagedQuest> {

    public StagedQuestView( Quest<StagedQuest> quest ) {
        super( quest );
    }

    @Override
    public Text getFormattedObjectives () {
        Text.Builder objectives = Text.builder();
        StagedQuest quest = (StagedQuest) super.quest;
        for ( Stage stage : quest.getStages() ) {
            if ( stage.equals( quest.getCurrent() ) ) {
                objectives.append( Text.of( stage.getObjective().toText() ) );
            } else {
                objectives.append( Text.of( TextStyles.ITALIC, TextColors.GRAY, stage.getObjective(), TextStyles.RESET, TextColors.RESET ) );
            }
        }
        return objectives.build();
    }
}
