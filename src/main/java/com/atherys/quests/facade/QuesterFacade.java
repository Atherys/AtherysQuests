package com.atherys.quests.facade;

import com.atherys.quests.service.QuesterService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class QuesterFacade {

    @Inject
    private QuesterService service;

}
