package com.atherys.quests.persistence;

import com.atherys.core.db.CachedHibernateRepository;
import com.atherys.quests.entity.SimpleAttemptedQuest;
import com.google.inject.Singleton;

@Singleton
public class AttemptedQuestRepository extends CachedHibernateRepository<SimpleAttemptedQuest, Long> {
    public AttemptedQuestRepository() {
        super(SimpleAttemptedQuest.class);
    }
}
