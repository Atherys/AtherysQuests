package com.atherys.quests;

import com.atherys.quests.data.DialogData;
import com.atherys.quests.data.QuestData;
import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;

public final class QuestKeys {

    public static Key<Value<String>> DIALOG;
    public static Key<Value<String>> QUEST;
    static DataRegistration<QuestData, QuestData.Immutable> QUEST_DATA_REGISTRATION;
    static DataRegistration<DialogData, DialogData.Immutable> DIALOG_DATA_REGISTRATION;

    static {
        DIALOG = Key.builder()
                .type(new TypeToken<Value<String>>() {
                })
                .id("dialog")
                .name("Dialog")
                .query(DataQuery.of("atherysquests", "Dialog"))
                .build();

        QUEST = Key.builder()
                .type(new TypeToken<Value<String>>() {
                })
                .id("Quest")
                .name("Quest")
                .query(DataQuery.of("atherysquests", "Quest"))
                .build();
    }

    QuestKeys() {
    }

}
