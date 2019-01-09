package com.atherys.quests.service;

import com.atherys.quests.api.base.Observer;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.model.SimpleQuester;
import com.atherys.quests.persistence.QuesterRepository;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Event;

import java.util.Optional;
import java.util.UUID;

public class QuesterService implements Observer<Event> {

    private QuesterRepository repository;

    public Quester getQuester(UUID uuid) {
        return null;
    }

    public Quester getQuester(User user) {
        return getQuester(user.getUniqueId());
    }

    public Player getCachedPlayer(Quester quester) {
        return ((SimpleQuester) quester).getCachedPlayer();
    }

    @Override
    public void notify(Event event, Quester quester) {

    }

    public void notify(Event event, Player player) {
        notify(event, getQuester(player));
    }

    public <T extends Quest> void turnInQuest(Player player, Quest<T> quest) {

    }

    public Optional<? extends User> getUser(Quester quester) {
        return null;
    }
}