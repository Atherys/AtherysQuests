package com.atherys.quests.quest.requirement;

import com.atherys.quests.quester.Quester;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;

public class LevelRequirement extends NumericRequirement {

    private int level;

    public LevelRequirement ( int level ) { super(level); }

    @Override
    public boolean check ( Quester quester ) {
        Player player = quester.getCachedPlayer();
        return player != null && check( player.get(Keys.EXPERIENCE_LEVEL).orElse(0) );
    }

    @Override
    public Requirement copy() {
        return new LevelRequirement( this.level );
    }
}
