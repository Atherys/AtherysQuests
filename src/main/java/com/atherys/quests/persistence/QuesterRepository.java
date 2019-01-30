package com.atherys.quests.persistence;

import com.atherys.core.db.HibernateRepository;
import com.atherys.quests.entity.SimpleQuester;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;

import java.util.UUID;

@Singleton
public class QuesterRepository extends HibernateRepository<SimpleQuester, UUID> {
    @Inject
    QuesterRepository(Logger logger) {
        super(SimpleQuester.class);
    }
}
