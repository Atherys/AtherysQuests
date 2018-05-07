package com.atherys.quests.api.requirement;

import com.atherys.quests.api.base.Prototype;
import com.atherys.quests.quester.Quester;
import org.spongepowered.api.text.TextRepresentable;

public interface Requirement extends Prototype<Requirement>, TextRepresentable {

    boolean check(Quester quester);

}
