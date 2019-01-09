package com.atherys.quests.facade;

import com.atherys.quests.service.QuestLocationService;
import com.atherys.quests.service.QuestService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class QuestFacade {

    @Inject
    private QuestService questService;

    @Inject
    private QuestLocationService questLocationService;

}
