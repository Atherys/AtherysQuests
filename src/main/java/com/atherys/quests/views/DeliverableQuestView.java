package com.atherys.quests.views;

import com.atherys.quests.quest.DeliverableQuest;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;

public class DeliverableQuestView<T extends QuestView, R extends DeliverableQuest> implements QuestView {
    private T view;
    private R quest;

    public DeliverableQuestView(T view, R quest) {
        this.view = view;
        this.quest = quest;
    }

    @Override
    public Text getFormattedRequirements() {
        return view.getFormattedRequirements();
    }

    @Override
    public Text getFormattedObjectives() {
        return view.getFormattedObjectives();
    }

    @Override
    public Text getFormattedRewards() {
        return view.getFormattedRewards();
    }

    @Override
    public BookView toBookView(Player player) {
        BookView oldView = view.toBookView(player);
        return BookView.builder()
                .from(oldView)
                .removePage(oldView.getPages().size() - 1)
                .addPage(getCompletion(player)).build();
    }

    @Override
    public Text getCompletion(Player player) {
        return Text.of("You've completed this quest! Return it to ", quest.getTargetName(), " to complete it.");
    }
}
