package com.atherys.quests;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.Value;

public final class QuestKeys {

    private QuestKeys() {}

    public final static Key<Value<String>> DIALOG;

    static {
        TypeToken<String> stringToken = TypeToken.of(String.class);
        TypeToken<Value<String>> valueStringToken = new TypeToken<Value<String>>(){};
        DIALOG = KeyFactory.makeSingleKey(stringToken, valueStringToken, DataQuery.of("Dialog"), "atherysquests:dialog", "Dialog");
    }

}
