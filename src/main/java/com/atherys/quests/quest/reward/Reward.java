package com.atherys.quests.quest.reward;

import com.atherys.quests.base.Prototype;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.views.TextViewable;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public interface Reward extends Prototype<Reward>, TextViewable {

    boolean award ( Quester quester );

}
