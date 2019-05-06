package com.atherys.quests.persistence;

import com.atherys.core.db.CachedHibernateRepository;
import com.atherys.quests.entity.SimpleQuester;
import com.google.inject.Singleton;

import java.util.UUID;

@Singleton
public class QuesterRepository extends CachedHibernateRepository<SimpleQuester, UUID> {
    QuesterRepository() {
        super(SimpleQuester.class);
    }
}
