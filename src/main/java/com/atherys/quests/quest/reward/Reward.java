package com.atherys.quests.quest.reward;

import com.atherys.quests.base.Prototype;
import com.atherys.quests.base.Viewable;
import com.atherys.quests.quester.Quester;

public interface Reward extends Prototype<Reward>, Viewable {

    boolean award ( Quester quester );

}
