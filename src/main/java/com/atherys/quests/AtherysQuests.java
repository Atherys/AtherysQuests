package com.atherys.quests;

import com.atherys.core.gson.ConfigurateAdapter;
import com.atherys.core.utils.RuntimeTypeAdapterFactory;
import com.atherys.quests.listeners.EntityListener;
import com.atherys.quests.listeners.InventoryListener;
import com.atherys.quests.listeners.MasterEventListener;
import com.atherys.quests.managers.QuestManager;
import com.atherys.quests.quest.Quest;
import com.atherys.quests.quest.objective.DialogObjective;
import com.atherys.quests.quest.objective.KillEntityObjective;
import com.atherys.quests.quest.objective.Objective;
import com.atherys.quests.quest.requirement.LevelRequirement;
import com.atherys.quests.quest.requirement.MoneyRequirement;
import com.atherys.quests.quest.requirement.QuestRequirement;
import com.atherys.quests.quest.requirement.Requirement;
import com.atherys.quests.quest.reward.MoneyReward;
import com.atherys.quests.quest.reward.MultiItemReward;
import com.atherys.quests.quest.reward.Reward;
import com.atherys.quests.quest.reward.SingleItemReward;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;

import java.io.IOException;
import java.util.Currency;
import java.util.Optional;

import static com.atherys.quests.AtherysQuests.*;

@Plugin( id = ID, name = NAME, description = DESCRIPTION, version = VERSION )
public class AtherysQuests {
    public static final String ID = "atherysquests";
    public static final String NAME = "A'therys Quests";
    public static final String DESCRIPTION = "A quest plugin written for the A'therys Horizons server.";
    public static final String VERSION = "1.0.0b";

    private static AtherysQuests instance;
    private static boolean init = false;
    private static QuestsConfig config;

    @Inject
    Logger logger;

    private void init() {
        // TODO: Dump assets into config file
        // TODO: Load dialogs from files
        instance = this;

        Sponge.getEventManager().registerListeners( this, new EntityListener() );
        Sponge.getEventManager().registerListeners( this, new InventoryListener() );
        Sponge.getEventManager().registerListeners( this, new QuestKeys() );

        try {
            config = new QuestsConfig("config/" + ID, "config.conf");
            config.init();
        } catch (IOException e) {
            init = false;
            e.printStackTrace();
            return;
        }

        init = true;
    }

    private void start() {

        Quest dummyQuest = Quest.builder( "dummyQuest", 1 )
                .name( Text.of("This is a dummy quest.") )
                .description( Text.of( "The purpose of this quest is to demonstrate that quests work. So uhh.. kill 3 unnamed creepers and 4 unnamed zombies. Also speak to the king at the end there. You'll get a magical anvil at the end for it." ) )
                .add( KillEntityObjective.of( "creeper", 3 ) )
                .add( KillEntityObjective.of( "zombie", 4 ) )
                //.add( new DialogObjective( "theKingSpeech", 14, Text.of("Speak to the king.") ) )
                .add( new SingleItemReward( ItemStack.builder().itemType(ItemTypes.ANVIL).quantity(1).add( Keys.DISPLAY_NAME, Text.of("The Magical Anvil") ).build() ) )
                .build();

        QuestManager.getInstance().registerQuest( dummyQuest );

        dummyQuest.getObjectives().forEach( obj -> logger.info("Quest has objective of type: " + obj.getClass().getName()));

        Sponge.getEventManager().registerListeners( this, new MasterEventListener() );

        RuntimeTypeAdapterFactory<Objective> objectiveTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Objective.class)
                .registerSubtype( KillEntityObjective.class )
                .registerSubtype( DialogObjective.class );

        RuntimeTypeAdapterFactory<Reward> rewardRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of( Reward.class )
                .registerSubtype( MoneyReward.class )
                .registerSubtype( MultiItemReward.class )
                .registerSubtype( SingleItemReward.class );

        RuntimeTypeAdapterFactory<Requirement> requirementRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of( Requirement.class )
                .registerSubtype( LevelRequirement.class )
                .registerSubtype( MoneyRequirement.class )
                .registerSubtype( QuestRequirement.class );

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter( Text.class, new ConfigurateAdapter<>( Text.class ) )
                .registerTypeAdapter( ItemStackSnapshot.class, new ConfigurateAdapter<>( ItemStackSnapshot.class ) )
                .registerTypeAdapter( Currency.class, new ConfigurateAdapter<>( Currency.class ) )
                .registerTypeAdapterFactory( objectiveTypeAdapterFactory )
                .registerTypeAdapterFactory( rewardRuntimeTypeAdapterFactory )
                .registerTypeAdapterFactory( requirementRuntimeTypeAdapterFactory )
                .create();

        String json = gson.toJson( dummyQuest, Quest.class );

        logger.info( json );

        Quest questSecond = gson.fromJson( json, Quest.class );

        logger.info( gson.toJson( questSecond, ItemStackSnapshot.class ) );

    }

    private void stop() {

    }

    @Listener
    public void onInit (GameInitializationEvent event) {
        init();
    }

    @Listener
    public void onStart (GameStartedServerEvent event) {
        if ( init ) start();
    }

    @Listener
    public void onStop (GameStoppingServerEvent event) {
        stop();
    }

    public static AtherysQuests getInstance() {
        return instance;
    }

    public static QuestsConfig getConfig() {
        return config;
    }

    public Optional<EconomyService> getEconomyService() {
        return Sponge.getServiceManager().provide( EconomyService.class );
    }
}
