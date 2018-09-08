package com.atherys.quests;

import com.atherys.core.command.CommandService;
import com.atherys.quests.api.script.DialogScriptService;
import com.atherys.quests.api.script.QuestScriptService;
import com.atherys.quests.command.dialog.DialogMasterCommand;
import com.atherys.quests.command.quest.QuestMasterCommand;
import com.atherys.quests.command.dialog.GetUUIDCommand;
import com.atherys.quests.data.DialogData;
import com.atherys.quests.data.QuestData;
import com.atherys.quests.gson.AtherysQuestsRegistry;
import com.atherys.quests.listener.EntityListener;
import com.atherys.quests.listener.GsonListener;
import com.atherys.quests.listener.InventoryListener;
import com.atherys.quests.listener.MasterEventListener;
import com.atherys.quests.managers.LocationManager;
import com.atherys.quests.managers.QuesterManager;
import com.atherys.quests.script.SimpleDialogScriptService;
import com.atherys.quests.script.SimpleQuestScriptService;
import com.atherys.quests.script.lib.QuestExtension;
import com.atherys.quests.service.*;
import com.atherys.script.js.JavaScriptLibrary;
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
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.economy.EconomyService;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static com.atherys.quests.AtherysQuests.*;

@Plugin(
        id = ID,
        name = NAME,
        description = DESCRIPTION,
        version = VERSION,
        dependencies = {
                @Dependency(id = "atheryscore"),
                @Dependency(id = "atherysscript")
        }
)
public class AtherysQuests {
    public static final String ID = "atherysquests";
    public static final String NAME = "A'therys Quests";
    public static final String DESCRIPTION = "A completedQuest plugin written for the A'therys Horizons server.";
    public static final String VERSION = "1.0.0b";
    private static AtherysQuests instance;
    private static boolean init = false;
    private static QuestsConfig config;

    private QuestService questService;
    private QuestAttachmentService questAttachmentService;
    private QuesterManager questerManager;
    private DialogService dialogService;
    private DialogAttachmentService dialogAttachmentService;
    private LocationManager locationManager;
    private InventoryService inventoryService;
    private ParticleEmitter particleEmitter;

    private DialogScriptService dialogScriptService;
    private QuestScriptService questScriptService;

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

        JavaScriptLibrary.getInstance().extendWith(QuestExtension.getInstance());

        dialogScriptService = SimpleDialogScriptService.getInstance();
        questScriptService = SimpleQuestScriptService.getInstance();

        try {
            questScriptService.registerFolder(new File("config/" + ID + "/quests"));
            dialogScriptService.registerFolder(new File("config/" + ID + "/dialogs"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        questService = QuestService.getInstance();
        dialogService = DialogService.getInstance();

        questAttachmentService= QuestAttachmentService.getInstance();
        dialogAttachmentService = DialogAttachmentService.getInstance();

        locationManager = LocationManager.getInstance();
        particleEmitter = ParticleEmitter.getInstance();

        questerManager = QuesterManager.getInstance();

        inventoryService = InventoryService.getInstance();

//        Quest completedQuest = new DummyQuest.Staged();
//
//        QuestService.getInstance().registerQuest(completedQuest);
//
//        DialogService.getInstance().registerDialog(DummyQuest.dialog("stagedQuestDialog", completedQuest));

        QuesterManager.getInstance().loadAll();
        LocationManager.getInstance().loadAll();

        try {
            CommandService.getInstance().register(new DialogMasterCommand(), this);
            CommandService.getInstance().register(new QuestMasterCommand(), this);
            CommandService.getInstance().register(new GetUUIDCommand(), this);
        } catch (CommandService.AnnotatedCommandException e) {
            e.printStackTrace();
        }
        particleEmitter.startEmitting();
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

        QuestKeys.QUEST_DATA_REGISTRATION = DataRegistration.<QuestData, QuestData.Immutable>builder()
                .dataClass(QuestData.class)
                .immutableClass(QuestData.Immutable.class)
                .builder(new QuestData.Builder())
                .dataName("Quest")
                .manipulatorId("completedQuest")
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

    public static QuestAttachmentService getQuestAttachmentService() {
        return getInstance().questAttachmentService;
    }

    public static DialogAttachmentService getDialogAttachmentService() {
        return getInstance().dialogAttachmentService;
    }

    public static DialogService getDialogService() {
        return getInstance().dialogService;
    }

    public static LocationManager getLocationManager() {
        return getInstance().locationManager;
    }

    public static InventoryService getInventoryService() {
        return getInstance().inventoryService;
    }

    public static QuestService getQuestService() {
        return getInstance().questService;
    }

    public static DialogScriptService getDialogScriptService() {
        return getInstance().dialogScriptService;
    }

    public static QuestScriptService getQuestScriptService() {
        return getInstance().questScriptService;
    }

    public Logger getLogger() {
        return logger;
    }
}
