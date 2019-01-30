package com.atherys.quests.persistence;

import com.atherys.core.db.HibernateRepository;
import com.atherys.quests.entity.QuestLocation;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;

import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@Singleton
public class QuestLocationRepository extends HibernateRepository<QuestLocation,Long> {
    @Inject
    QuestLocationRepository(Logger logger) {
        super(QuestLocation.class);
    }

    public Stream<QuestLocation> parallelStream() {
        return getCache().values().parallelStream();
    }
}