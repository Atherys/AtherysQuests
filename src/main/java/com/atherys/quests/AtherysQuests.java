package com.atherys.quests;

import com.google.gson.Gson;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.economy.EconomyService;

import java.util.Optional;

import static com.atherys.quests.AtherysQuests.*;

@Plugin( id = ID, name = NAME, description = DESCRIPTION, version = VERSION )
public class AtherysQuests {
    public static final String ID = "atherysquests";
    public static final String NAME = "A'therys Quests";
    public static final String DESCRIPTION = "A quest plugin written for the A'therys Horizons server.";
    public static final String VERSION = "1.0.0a";

    private static AtherysQuests instance = new AtherysQuests();
    private static boolean init = false;
    private static QuestsConfig config;

    private EconomyService economyService;

    private Gson gson = new Gson();

    private void init() {
        economyService = Sponge.getServiceManager().provide( EconomyService.class ).orElse(null);
        // TODO: Dump assets into config file
        // TODO: Load dialogs from files
        init = true;
    }

    private void start() {
        config = new QuestsConfig();
    }

    private void stop() {

    }

    @Listener
    public void onInit (GameInitializationEvent event) {
        init();
    }

    @Listener
    public void onStart (GameStartingServerEvent event) {
        if ( init ) start();
    }

    @Listener
    public void onStop (GameStoppingServerEvent event) {
        stop();
    }

    public Gson getGson() {
        return gson;
    }

    public static AtherysQuests getInstance() {
        return instance;
    }

    public static QuestsConfig getConfig() {
        return config;
    }

    public Optional<EconomyService> getEconomyService() {
        return Optional.ofNullable( economyService );
    }
}
