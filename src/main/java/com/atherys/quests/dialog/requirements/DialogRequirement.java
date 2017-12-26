package com.atherys.quests.dialog.requirements;

import org.spongepowered.api.entity.living.player.Player;

public interface DialogRequirement {

    boolean check ( Player player );

}
