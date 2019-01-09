package com.atherys.quests.persistence;

import com.atherys.core.db.AtherysRepository;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.model.QuestLocation;
import com.google.inject.Singleton;

@Singleton
public class QuestLocationRepository extends AtherysRepository<QuestLocation,Long> {
    public QuestLocationRepository() {
        super(QuestLocation.class, AtherysQuests.getInstance().getLogger());
    }
}