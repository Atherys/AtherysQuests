package com.atherys.quests.db;

import com.atherys.core.database.mongo.MorphiaDatabase;
import com.atherys.core.utils.GsonTypeConverter;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import org.spongepowered.api.world.Location;

public class QuestsDatabase extends MorphiaDatabase {

    private static QuestsDatabase instance = new QuestsDatabase();

    protected QuestsDatabase() {
        super(AtherysQuests.getConfig().DATABASE, "com.atherys.quests");
        super.getMorphia().getMapper().getConverters().addConverter(new GsonTypeConverter<>(Quest.class, AtherysQuests.getGson()));
        super.getMorphia().getMapper().getConverters().addConverter(new GsonTypeConverter<>(Location.class, AtherysQuests.getGson()));
    }

    public static QuestsDatabase getInstance() {
        return instance;
    }
}
