package com.atherys.quests.quest.requirement;

import com.atherys.quests.base.Prototype;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.util.RuntimeTypeAdapterFactory;
import com.atherys.quests.views.TextViewable;

public interface Requirement extends Prototype<Requirement>, TextViewable {

    default RuntimeTypeAdapterFactory<Requirement> factory() {
        return RuntimeTypeAdapterFactory.of(Requirement.class);
    }

    boolean check ( Quester quester );

}
