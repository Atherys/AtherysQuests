package com.atherys.quests.persistence;

import com.atherys.core.db.AtherysRepository;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.model.SimpleQuester;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;

import java.util.UUID;

@Singleton
public class QuesterRepository extends AtherysRepository<SimpleQuester, UUID> {
    @Inject
    QuesterRepository(Logger logger) {
        super(SimpleQuester.class, logger);
    }
}
