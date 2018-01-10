package com.atherys.quests;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;

public final class QuestKeys {

    private QuestKeys() {}

    public final static Key<Value<String>> DIALOG = Key.builder().type(new TypeToken<Value<String>>(){}).query( DataQuery.of("Dialog") ).build();

    public final static Key<Value<String>> QUEST = Key.builder().type(new TypeToken<Value<String>>(){}).query( DataQuery.of("Quest") ).build();

}
