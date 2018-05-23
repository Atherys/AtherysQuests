package com.atherys.quests.managers;

import com.atherys.core.database.mongo.MorphiaDatabaseManager;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.db.QuestsDatabase;
import com.atherys.quests.quester.Quester;
import com.google.gson.Gson;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;

import java.util.Optional;
import java.util.UUID;

public final class QuesterManager extends MorphiaDatabaseManager<Quester> {

    private static QuesterManager instance = new QuesterManager();

    private Gson gson;

    protected QuesterManager() {
        super(QuestsDatabase.getInstance(), AtherysQuests.getInstance().getLogger(), Quester.class);
    }

    public static QuesterManager getInstance() {
        return instance;
    }

    /**
     * Creates a Quester object for the Player and stores it into the database. Also caches it for easier access.
     *
     * @param player The player who is to have a Quester object created.
     * @return The Quester object.
     */
    public Quester createQuester(Player player) {
        Quester quester = new Quester(player);
        addQuester(quester);
        return quester;
    }

    public void addQuester(Quester quester) {
        this.getCache().put(quester.getUUID(), quester);
        this.save(quester);
    }

    /**
     * Gets a player's Quester object. If player has no associated Quester object, this method will create one via {@link #createQuester(Player)}.
     *
     * @param player the player whose Quester object is to be retrieved
     * @return The question object
     */
    public Quester getQuester(Player player) {
        if (getCache().containsKey(player.getUniqueId())) {
            return getCache().get(player.getUniqueId());
        } else {
            return createQuester(player);
        }
    }

    /**
     * Returns whether or not the player has an associated Quester object.
     *
     * @param player The player whose Quester object is to be tested for
     * @return true if a Quester object is found, false if not
     */
    public boolean hasQuester(Player player) {
        return getCache().containsKey(player.getUniqueId());
    }

    public void notify(Event event, Player player) {
        getQuester(player).notify(event, player);
    }

    /**
     * Do not use this method. Instead, use {@link #getQuester(Player)}.
     *
     * @param uuid UUID of player
     * @return An empty optional
     */
    @Override
    public Optional<Quester> get(UUID uuid) {
        return Optional.ofNullable(getCache().get(uuid));
    }

    @Override
    public void loadAll() {
        gson = AtherysQuests.getGson();
        super.loadAll();
    }

    public void saveAll() {
        saveAll(getCache().values());
    }
}
