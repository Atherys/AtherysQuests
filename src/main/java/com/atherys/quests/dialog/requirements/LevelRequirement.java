package com.atherys.quests.dialog.requirements;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;

public class LevelRequirement extends DoubleRequirement {

    public LevelRequirement ( double amount ) {
        super(amount);
    }

    @Override
    public boolean check ( Player player ) {
        return check( player.get( Keys.EXPERIENCE_LEVEL ).orElse(0) );
    }
}
