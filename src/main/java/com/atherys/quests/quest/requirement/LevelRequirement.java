package com.atherys.quests.quest.requirement;

import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.quester.Quester;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

/**
 * A requirement on the {@link Keys#EXPERIENCE_LEVEL} of the given player.
 */
public class LevelRequirement extends NumericRequirement {

    LevelRequirement( int level ) {
        super( level );
    }

    @Override
    public boolean check( Quester quester ) {
        Player player = quester.getCachedPlayer();
        return player != null && check( player.get( Keys.EXPERIENCE_LEVEL ).orElse( 0 ) );
    }

    @Override
    public Requirement copy() {
        return new LevelRequirement( (int) this.number );
    }

    @Override
    public Text toText() {
        return Text.of( "You must have an enchantment level of at least ", number );
    }
}
