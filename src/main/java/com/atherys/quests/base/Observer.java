package com.atherys.quests.base;

import org.spongepowered.api.event.Event;

public interface Observer<T extends Event> {

    void notify ( T event );

}
