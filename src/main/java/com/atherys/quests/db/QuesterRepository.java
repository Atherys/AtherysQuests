package com.atherys.quests.db;

import com.atherys.core.db.AtherysRepository;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.quester.Quester;

import java.util.UUID;

public class QuesterRepository extends AtherysRepository<Quester, UUID> {

    private static final QuesterRepository instance = new QuesterRepository();

    private QuesterRepository() {
        super(Quester.class, AtherysQuests.getInstance().getLogger());
    }

    public static QuesterRepository getInstance() {
        return instance;
    }
}
