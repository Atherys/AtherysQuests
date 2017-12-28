package com.atherys.quests.quest.objective;

import com.atherys.quests.base.Prototype;
import com.atherys.quests.base.Observer;
import org.spongepowered.api.event.Event;

public interface Objective<T extends Event> extends Prototype<Objective<T>>, Observer<T> {

    boolean isComplete();

}
