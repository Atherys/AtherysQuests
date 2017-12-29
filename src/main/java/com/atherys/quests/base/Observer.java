package com.atherys.quests.base;

import com.atherys.quests.quester.Quester;
import org.spongepowered.api.event.Event;

public interface Observer<T extends Event> {

    void notify ( T event, Quester quester );

}
