package com.atherys.quests.db;

import com.atherys.core.database.mongo.AbstractMongoDatabase;
import com.atherys.quests.AtherysQuests;

public class QuestsDatabase extends AbstractMongoDatabase {

    private static QuestsDatabase instance = new QuestsDatabase();

    protected QuestsDatabase( ) {
        super( AtherysQuests.getConfig().DATABASE );
    }

    public static QuestsDatabase getInstance() {
        return instance;
    }
}
