package com.atherys.quests.service;

public class QuesterService {

    private static final QuesterService instance = new QuesterService();

    public static QuesterService getInstance() {
        return instance;
    }

}