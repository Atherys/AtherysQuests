package com.atherys.quests.persistence;

import com.atherys.core.db.AtherysRepository;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.model.SimpleQuester;
import com.google.inject.Singleton;

import java.util.UUID;

@Singleton
public class QuesterRepository extends AtherysRepository<SimpleQuester, UUID> {
    private QuesterRepository() {
        super(SimpleQuester.class, AtherysQuests.getInstance().getLogger());
    }
}
