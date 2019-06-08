package com.atherys.quests.script.lib.quest;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import org.spongepowered.api.entity.living.player.Player;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * @jsfunc
 */
public class SetOnTimedQuestFail implements BiFunction<Quest<?>, Consumer<Player>, Boolean> {
    /**
     * Sets a function that will run if the quest's timer runs out.
     * @ex var world = getWorldFromName("world");
     * @ex var onFail = function(player) {
     * @ex      teleportPlayer(player, locationOf(world, 100, 90, 100);
     * @ex }
     * @ex setOnTimedQuestFail(quest, onFail);
     * @param quest The quest.
     * @param onFail A function that will run if the quest's time runs out. It takes a single argument, the player
     * @return True if setting it worked, false if the quest isn't timed.
     */
    @Override
    public Boolean apply(Quest<?> quest, Consumer<Player> onFail) {
        if (quest.getTimedComponent().isPresent()) {
            quest.getTimedComponent().get().setOnComplete(onFail);
            return true;
        } else {
            return false;
        }
    }
}
