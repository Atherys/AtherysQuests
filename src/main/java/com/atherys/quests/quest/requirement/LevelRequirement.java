package com.atherys.quests.quest.requirement;

import com.atherys.quests.quester.Quester;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

@ConfigSerializable
public class LevelRequirement extends NumericRequirement {

    @Setting
    private int level;

    private LevelRequirement() {
        super(0);
    }

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

    @Override
    public Text toText() {
        return Text.of( "You must have an enchantment level of at least ", level );
    }
}
