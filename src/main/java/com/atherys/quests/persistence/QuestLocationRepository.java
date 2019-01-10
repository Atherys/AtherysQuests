package com.atherys.quests.persistence;

import com.atherys.core.db.AtherysRepository;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.model.QuestLocation;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;

@Singleton
public class QuestLocationRepository extends AtherysRepository<QuestLocation,Long> {
    @Inject
    QuestLocationRepository(Logger logger) {
        super(QuestLocation.class, logger);
    }
}