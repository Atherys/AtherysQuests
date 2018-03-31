package com.atherys.quests.quest.requirement;

import com.atherys.quests.base.Prototype;
import com.atherys.quests.quester.Quester;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.text.TextRepresentable;

@ConfigSerializable
public interface Requirement extends Prototype<Requirement>, TextRepresentable {

    boolean check( Quester quester );

}
