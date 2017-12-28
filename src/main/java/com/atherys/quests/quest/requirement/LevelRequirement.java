package com.atherys.quests.quest.requirement;

import com.atherys.quests.quester.Quester;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.User;

import java.util.Optional;

public class LevelRequirement implements Requirement {

    private int level;

    public LevelRequirement ( int level ) {
        this.level = level;
    }

    @Override
    public boolean check ( Quester player ) {
        Optional<User> user = player.getUser();
        if ( !user.isPresent() ) return false;

        int playerLevel = user.get().get(Keys.EXPERIENCE_LEVEL ).orElse(0);
        return playerLevel >= this.level;
    }

    @Override
    public Requirement copy() {
        return new LevelRequirement( this.level );
    }
}
