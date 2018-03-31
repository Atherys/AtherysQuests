package com.atherys.quests.quest.reward;

import com.atherys.quests.base.Prototype;
import com.atherys.quests.quester.Quester;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.text.TextRepresentable;

@ConfigSerializable
public interface Reward extends Prototype<Reward>, TextRepresentable {

    boolean award( Quester quester );

}
