package com.atherys.quests;

import com.atherys.quests.data.DialogData;
import com.atherys.quests.data.QuestData;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.util.generator.dummy.DummyObjectProvider;

public final class QuestKeys {

    QuestKeys() {
    }

    public static Key<Value<String>> DIALOG = DummyObjectProvider.createExtendedFor( Key.class, "DIALOG" );
    public static Key<Value<String>> QUEST = DummyObjectProvider.createExtendedFor( Key.class, "QUEST" );

    protected static DataRegistration<QuestData, QuestData.Immutable> QUEST_DATA_REGISTRATION;
    protected static DataRegistration<DialogData, DialogData.Immutable> DIALOG_DATA_REGISTRATION;

}
