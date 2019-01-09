package com.atherys.quests.service;

import com.atherys.quests.api.base.Observer;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.persistence.QuesterRepository;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Event;

import java.util.UUID;

public class QuesterService implements Observer<Event> {

    private static final QuesterService instance = new QuesterService();

    private QuesterRepository repository;

    public Quester getQuester(UUID uuid) {
        return null;
    }

    public Quester getQuester(User user) {
        return getQuester(user.getUniqueId());
    }

    public static QuesterService getInstance() {
        return instance;
    }

    @Override
    public void notify(Event event, Quester quester) {

    }

    public void notify(Event event, Player player) {
        notify(event, getQuester(player));
    }
}