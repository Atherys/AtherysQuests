package com.atherys.quests.views.api;

import com.atherys.quests.base.Viewable;
import com.atherys.quests.views.ViewManager;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;

public interface View {

    static Optional<View> of ( Viewable object ) {
        return ViewManager.getInstance().getView( object );
    }

    void show ( Player player );

}
