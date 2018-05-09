package com.atherys.quests.managers;

import com.atherys.core.database.mongo.AbstractMongoDatabaseManager;
import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.db.QuestsDatabase;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.util.GsonUtils;
import com.google.gson.Gson;
import org.bson.Document;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;

import java.util.Optional;
import java.util.UUID;

public final class QuesterManager extends AbstractMongoDatabaseManager<Quester> {

    private static Gson gson = GsonUtils.getGson();

    private static QuesterManager instance = new QuesterManager();

    protected QuesterManager() {
        super(AtherysQuests.getInstance().getLogger(), QuestsDatabase.getInstance(), "questers");
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
        if(getCache().containsKey(player.getUniqueId())) {
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

    public static QuesterManager getInstance() {
        return instance;
    }

    /**
     * Do not use this method. Instead, use {@link #getQuester(Player)}.
     *
     * @param uuid UUID of player
     * @return An empty optional
     */
    @Override
    public Optional<Quester> get(UUID uuid){
        return Optional.ofNullable(getCache().get(uuid));
    }

    @Override
    protected Optional<Document> toDocument(Quester quester) {
        Document document = new Document();
        document.append("uuid", quester.getUUID());

        Document quests = new Document();
        quester.getQuests().forEach((k, v) -> quests.append(k, gson.toJson(v)));
        document.append("quests", quests);

        Document completedQuests = new Document();
        quester.getCompletedQuests().forEach(completedQuests::append);
        document.append("completedQuests", completedQuests);

        return Optional.of(document);
    }

    @Override
    protected Optional<Quester> fromDocument(Document document) {
        UUID uuid = document.get("uuid", UUID.class);

        Quester quester = new Quester(uuid);

        Document quests = document.get("quests", Document.class);
        quests.forEach((k, v) -> quester.getQuests().put(k, gson.fromJson((String) v, Quest.class)));

        Document completedQuests = document.get("completedQuests", Document.class);
        completedQuests.forEach((k, v) -> quester.getCompletedQuests().put(k, (long) v));

        return Optional.of(quester);
    }

    public void saveAll() {
        saveAll(getCache().values());
    }
}
