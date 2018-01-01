package com.atherys.quests.quest.requirement;

import com.atherys.quests.base.Prototype;
import com.atherys.quests.base.Viewable;
import com.atherys.quests.quester.Quester;

public interface Requirement extends Prototype<Requirement>, Viewable {

    boolean check ( Quester quester );

}
