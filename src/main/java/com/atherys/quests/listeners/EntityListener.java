package com.atherys.quests.listeners;

import com.atherys.core.utils.Question;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.managers.DialogManager;
import com.atherys.quests.managers.LocationManager;
import com.atherys.quests.managers.QuestManager;
import com.atherys.quests.managers.QuesterManager;
import com.atherys.quests.util.QuestMsg;
import com.atherys.quests.views.TakeQuestView;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.Optional;

public class EntityListener {

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
    }

    @Listener
    public void onEntityInteract(InteractEntityEvent.Secondary.MainHand event, @Root Player player) {
        DialogManager.getInstance().startDialog(player, event.getTargetEntity());
    }

    @Listener
    public void onPlayerMove(MoveEntityEvent e, @Root Player player){
        if(e.getFromTransform().getLocation().getBlockPosition().equals
                (e.getToTransform().getLocation().getBlockPosition())) return;
        if(LocationManager.getInstance().getByLocation(e.getFromTransform().getLocation()).isPresent()) return;

        LocationManager.getInstance().getByLocation(e.getToTransform().getLocation()).ifPresent(questLocation -> {
            Quest quest = QuestManager.getInstance().getQuest(questLocation.getQuestId()).get();
            if(QuesterManager.getInstance().getQuester(player).hasQuest(quest)) return;

            Question question = Question.of(Text.of("You have found the quest \"", quest.getName(), "\", would you like to take it?"))
                    .addAnswer(Question.Answer.of(Text.of(TextStyles.BOLD, TextColors.DARK_GREEN, "Yes"), quester -> {
                        if(questLocation.contains(player.getLocation())) {
                            new TakeQuestView(quest).show(quester);
                        } else {
                            QuestMsg.error(quester, "You have left the quest area.");
                        }
                    }))
                    .addAnswer(Question.Answer.of(Text.of(TextStyles.BOLD, TextColors.DARK_RED, "No"), quester -> {
                        QuestMsg.error(quester, "You have declined the quest \"", quest.getName(), "\".");
                    }))
                    .build();
            question.pollChat(player);
        });
    }

    @Listener
    public void onLeftClick(InteractBlockEvent.Secondary event, @Root Player player) {
        Optional<ItemStack> itemInHand = player.getItemInHand(HandTypes.MAIN_HAND);
        if(!itemInHand.isPresent()) return;

        Optional<Quest> quest = QuestManager.getInstance().getQuest(itemInHand.get());
        if(!quest.isPresent()) return;

        event.setCancelled(true);

        new TakeQuestView(quest.get()).show(player);

        //quester.get().pickupQuest( quest.get() );
    }

}
