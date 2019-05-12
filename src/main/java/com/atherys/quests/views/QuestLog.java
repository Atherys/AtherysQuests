package com.atherys.quests.views;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.ArrayList;
import java.util.List;

public class QuestLog {

    private final Quester simpleQuester;

    public QuestLog(Quester simpleQuester) {
        this.simpleQuester = simpleQuester;
    }

    public void show(Player player) {
        BookView.Builder log = BookView.builder();

        List<Text> pages = new ArrayList<>();
        Text.Builder lastPage = Text.builder();

        lastPage.append(Text.of("Quest Log:\n"));

        int i = 1;
        for (Quest quest : simpleQuester.getOngoingQuests()) {
            Text.Builder questView = Text.builder();
            questView.append(Text.of("[", i, "] "));
            if (quest.isComplete()) {
                questView.append(Text.of(TextStyles.STRIKETHROUGH, quest.getName(), TextStyles.NONE));
            } else if (quest.isFailed()) {
                questView.append(Text.of(TextColors.RED, TextStyles.STRIKETHROUGH, quest.getName(), TextStyles.RESET));
            } else {
                questView.append(Text.of(quest.getName()));
            }
            questView.onHover(TextActions.showText(Text.of("Click to view more details.")));
            questView.onClick(TextActions.executeCallback(src -> quest.createView().show(player)));

            if (i % 7 == 0) {
                pages.add(lastPage.build());
                lastPage = Text.builder();
            } else {
                lastPage.append(Text.of(questView.build(), "\n"));
            }

            i++;
        }

        pages.add(lastPage.build());

        log.addPages(pages);

        player.sendBookView(log.build());
    }

}
