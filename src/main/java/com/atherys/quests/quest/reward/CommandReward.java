package com.atherys.quests.quest.reward;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quester.Quester;
import com.atherys.quests.api.reward.Reward;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * A reward which executes a command as the server console. Supports the following placeholders:
 * ${player} = The player name
 * ${world} = The player's world
 * ${x}, ${y}, ${z} = The player's coordinates
 */
public class CommandReward implements Reward {

    public static final String PLAYER_NAME_PLACEHOLDER = "${player}";
    public static final String PLAYER_WORLD_PLACEHOLDER = "${world}";
    public static final String PLAYER_X_PLACEHOLDER = "${x}";
    public static final String PLAYER_Y_PLACEHOLDER = "${y}";
    public static final String PLAYER_Z_PLACEHOLDER = "${z}";

    private String commandWithPlaceholders;

    private Text description;

    CommandReward(String commandWithPlaceholders, Text description) {
        this.commandWithPlaceholders = commandWithPlaceholders;
        this.description = description;
    }

    @Override
    public boolean award(Quester quester) {
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), getCommand(quester));
        return true;
    }

    @Override
    public Reward copy() {
        return new CommandReward(commandWithPlaceholders, description);
    }

    @Override
    public Text toText() {
        return description;
    }

    public String getCommandWithPlaceholders() {
        return commandWithPlaceholders;
    }

    public String getCommand(Quester quester) {
        Player cachedPlayer = AtherysQuests.getInstance().getQuesterService().getCachedPlayer(quester);

        if (cachedPlayer == null) throw new RuntimeException("Cached Player in CommandReward was null");

        Location<World> location = cachedPlayer.getLocation();

        return commandWithPlaceholders
                .replace(PLAYER_NAME_PLACEHOLDER, cachedPlayer.getName())
                .replace(PLAYER_WORLD_PLACEHOLDER, location.getExtent().getName())
                .replace(PLAYER_X_PLACEHOLDER, String.valueOf(location.getX()))
                .replace(PLAYER_Y_PLACEHOLDER, String.valueOf(location.getY()))
                .replace(PLAYER_Z_PLACEHOLDER, String.valueOf(location.getZ()));
    }
}