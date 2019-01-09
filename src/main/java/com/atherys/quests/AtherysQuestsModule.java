package com.atherys.quests;

import com.atherys.quests.persistence.QuestLocationRepository;
import com.atherys.quests.persistence.QuesterRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class AtherysQuestsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(QuesterRepository.class);
        bind(QuestLocationRepository.class);
    }
}
