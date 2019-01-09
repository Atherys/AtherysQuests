package com.atherys.quests.api.reward;

import com.atherys.quests.api.base.Prototype;
import com.atherys.quests.api.quester.Quester;
import org.spongepowered.api.text.TextRepresentable;

public interface Reward extends Prototype<Reward>, TextRepresentable {

    boolean award(Quester quester);

}
