package com.atherys.quests;

import com.atherys.core.command.CommandService;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.script.ScriptService;
import com.atherys.quests.commands.dialog.DialogMasterCommand;
import com.atherys.quests.commands.quest.QuestMasterCommand;
import com.atherys.quests.data.DialogData;
import com.atherys.quests.data.QuestData;
import com.atherys.quests.gson.AtherysQuestsRegistry;
import com.atherys.quests.listeners.EntityListener;
import com.atherys.quests.listeners.GsonListener;
import com.atherys.quests.listeners.InventoryListener;
import com.atherys.quests.listeners.MasterEventListener;
import com.atherys.quests.managers.*;
import com.atherys.quests.script.JavaScriptService;
import com.google.gson.Gson;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.economy.EconomyService;

import java.io.IOException;
import java.util.Optional;

import static com.atherys.quests.AtherysQuests.*;

@Plugin(id = ID, name = NAME, description = DESCRIPTION, version = VERSION)
public class AtherysQuests {
    public static final String ID = "atherysquests";
    public static final String NAME = "A'therys Quests";
    public static final String DESCRIPTION = "A quest plugin written for the A'therys Horizons server.";
    public static final String VERSION = "1.0.0b";
    private static AtherysQuests instance;
    private static boolean init = false;
    private static QuestsConfig config;

    private ScriptService scriptService;
    private QuestManager questManager;
    private QuesterManager questerManager;
    private DialogManager dialogManager;
    private LocationManager locationManager;
    private InventoryManager inventoryManager;

    @Inject
    PluginContainer container;

    @Inject
    Logger logger;

    public static AtherysQuests getInstance() {
        return instance;
    }

    public static QuestsConfig getConfig() {
        return config;
    }

    public static AtherysQuestsRegistry getRegistry() {
        return AtherysQuestsRegistry.getInstance();
    }

    public static Gson getGson() {
        return getRegistry().getGson();
    }

    private void init() {
        instance = this;

        try {
            config = new QuestsConfig(getWorkingDirectory(), "config.conf");
            config.init();
        } catch (IOException e) {
            init = false;
            e.printStackTrace();
            return;
        }

        if (config.IS_DEFAULT) {
            logger.error("The AtherysQuests config is set to default. Please change default config settings and then set 'isDefault' to 'false'.");
            init = false;
            return;
        }

        init = true;
    }

    private void start() {

        Sponge.getEventManager().registerListeners(this, new GsonListener());
        Sponge.getEventManager().registerListeners(this, new EntityListener());
        Sponge.getEventManager().registerListeners(this, new InventoryListener());
        Sponge.getEventManager().registerListeners(this, new MasterEventListener());

        scriptService = JavaScriptService.getInstance();
        dialogManager = DialogManager.getInstance();
        questManager = QuestManager.getInstance();
        questerManager = QuesterManager.getInstance();
        locationManager = LocationManager.getInstance();

        inventoryManager = InventoryManager.getInstance();

        Quest quest = new DummyQuest.Staged();

        QuestManager.getInstance().registerQuest(quest);

        DialogManager.getInstance().registerDialog(DummyQuest.dialog("stagedQuestDialog", quest));

        QuesterManager.getInstance().loadAll();
        LocationManager.getInstance().loadAll();

        try {
            CommandService.getInstance().register(new DialogMasterCommand(), this);
            CommandService.getInstance().register(new QuestMasterCommand(), this);
        } catch (CommandService.AnnotatedCommandException e) {
            e.printStackTrace();
        }
    }

    private void stop() {
        QuesterManager.getInstance().saveAll();
        LocationManager.getInstance().saveAll();
    }

    @Listener
    public void preInit(GamePreInitializationEvent event) {
        QuestKeys.DIALOG_DATA_REGISTRATION = DataRegistration.builder()
                .dataClass(DialogData.class)
                .immutableClass(DialogData.Immutable.class)
                .builder(new DialogData.Builder())
                .dataName("Dialog")
                .manipulatorId("dialog")
                .buildAndRegister(this.container);

        QuestKeys.QUEST_DATA_REGISTRATION = DataRegistration.builder()
                .dataClass(QuestData.class)
                .immutableClass(QuestData.Immutable.class)
                .builder(new QuestData.Builder())
                .dataName("Quest")
                .manipulatorId("quest")
                .buildAndRegister(this.container);
    }

    @Listener
    public void onInit(GameInitializationEvent event) {
        init();
    }

    @Listener
    public void onStart(GameStartedServerEvent event) {
        if (init) start();
    }

    @Listener
    public void onStop(GameStoppingServerEvent event) {
        if (init) stop();
    }

    public String getWorkingDirectory() {
        return "config/" + ID;
    }

    public Optional<EconomyService> getEconomyService() {
        return Sponge.getServiceManager().provide(EconomyService.class);
    }

    public static QuesterManager getQuesterManager() {
        return getInstance().questerManager;
    }

    public static DialogManager getDialogManager() {
        return getInstance().dialogManager;
    }

    public static LocationManager getLocationManager() {
        return getInstance().locationManager;
    }

    public static InventoryManager getInventoryManager() {
        return getInstance().inventoryManager;
    }

    public static QuestManager getQuestManager() {
        return getInstance().questManager;
    }

    public static ScriptService getScriptService() {
        return getInstance().scriptService;
    }

    public Logger getLogger() {
        return logger;
    }
}
