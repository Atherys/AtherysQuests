package com.atherys.quests;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.Value;

public final class QuestKeys {

    private QuestKeys() {}

    public final static Key<Value<String>> DIALOG;
    public final static Key<Value<String>> QUEST;

    static {
        TypeToken<String> dialogStringToken = TypeToken.of(String.class);
        TypeToken<Value<String>> dialogValueStringToken = new TypeToken<Value<String>>(){};
        DIALOG = KeyFactory.makeSingleKey(dialogStringToken, dialogValueStringToken, DataQuery.of("Dialog"), "atherysquests:dialog", "Dialog");

        TypeToken<String> stringToken = TypeToken.of(String.class);
        TypeToken<Value<String>> valueStringToken = new TypeToken<Value<String>>(){};
        QUEST = KeyFactory.makeSingleKey(stringToken, valueStringToken, DataQuery.of("Quest"), "atherysquests:quest", "Quest");
    }

}
