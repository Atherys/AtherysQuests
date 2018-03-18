package com.atherys.quests;

import com.atherys.quests.listeners.EntityListener;
import com.atherys.quests.listeners.InventoryListener;
import com.atherys.quests.listeners.MasterEventListener;
import com.atherys.quests.managers.QuestManager;
import com.atherys.quests.quest.Quest;
import com.atherys.quests.quest.objective.DialogObjective;
import com.atherys.quests.quest.objective.KillEntityObjective;
import com.atherys.quests.quest.objective.Objective;
import com.atherys.quests.quest.objective.ObjectiveAdapter;
import com.atherys.quests.quest.reward.SingleItemReward;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
                .add( new DialogObjective( "theKingSpeech", 14, Text.of("Speak to the king.") ) )
                .add( new SingleItemReward( ItemStack.builder().itemType(ItemTypes.ANVIL).quantity(1).add( Keys.DISPLAY_NAME, Text.of("The Magical Anvil") ).build() ) )
                .build();

        QuestManager.getInstance().registerQuest( dummyQuest );

        dummyQuest.getObjectives().forEach( obj -> logger.info("Quest has objective of type: " + obj.getClass().getName()));

        Sponge.getEventManager().registerListeners( this, new MasterEventListener() );
        //QuestManager.getInstance().unregisterQuest ( dummyQuest );

        Objective objective = KillEntityObjective.of("zombie", 8);

        GsonConfigurationLoader loader = GsonConfigurationLoader.builder().build();
        ConfigurationNode node = loader.createEmptyNode( ConfigurationOptions.defaults() );

        Set<Class<?>> types = new HashSet<>();
        types.add(KillEntityObjective.class);
        types.add(DialogObjective.class);

        node.getOptions().setAcceptedTypes( types );

        TypeSerializers.getDefaultSerializers().registerType( new TypeToken<Objective>() {}, new ObjectiveAdapter() );

        try {
            node.setValue(objective);
            loader.saveInternal(node, System.console().writer());

            try {
                Objective quest = node.getValue( TypeToken.of(Objective.class) );
                ConfigurationNode newNode = loader.createEmptyNode( ConfigurationOptions.defaults() );
                newNode.getOptions().setAcceptedTypes(types);
                try {
                    newNode.setValue(quest);
                    loader.saveInternal( node, System.console().writer() );
                } catch (IOException e) {
                    logger.info("2. Failed to write to console writer.");
                }
            } catch ( ObjectMappingException e ) {
                logger.info("Failed to map Gson config node to Quest");
                e.printStackTrace();
            }

        } catch (IOException e) {
            logger.info("1. Failed to write to console writer.");
            e.printStackTrace();
        }

       /* ObjectMapper mapper = new ObjectMapper();

        try {
            String jacksonJson = mapper.writeValueAsString( dummyQuest );
            logger.info(jacksonJson);

            Quest quest = mapper.readValue( jacksonJson, Quest.class );

            String secondJson = mapper.writeValueAsString( quest );
            logger.info(secondJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*GsonConfigurationLoader loader = GsonConfigurationLoader.builder().build();
        ConfigurationNode node = loader.createEmptyNode( ConfigurationOptions.defaults() );

        TypeSerializers.getDefaultSerializers().registerType( new TypeToken<List<Objective>>() {}, new ObjectiveListTypeSerializer() );

        try {
            node.setValue(TypeToken.of(Quest.class), dummyQuest);
            loader.saveInternal(node, System.console().writer());

            try {
                Quest quest = node.getValue(TypeToken.of(Quest.class));
                ConfigurationNode newNode = loader.createEmptyNode( ConfigurationOptions.defaults() );
                try {
                    newNode.setValue(TypeToken.of(Quest.class), quest);
                    loader.saveInternal( node, System.console().writer() );
                } catch (ObjectMappingException e) {
                    logger.info("2. Failed to map DummyQuest to Gson ConfigurationNode.");
                } catch (IOException e) {
                    logger.info("2. Failed to write to console writer.");
                }
            } catch ( ObjectMappingException e ) {
                logger.info("Failed to map Gson config node to Quest");
                e.printStackTrace();
            }

        } catch (IOException e) {
            logger.info("1. Failed to write to console writer.");
        } catch (ObjectMappingException e) {
            logger.info("1. Failed to map DummyQuest to Gson ConfigurationNode.");
            e.printStackTrace();
        }*/

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
