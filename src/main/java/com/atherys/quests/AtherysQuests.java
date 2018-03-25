package com.atherys.quests;

import com.atherys.quests.data.DialogData;
import com.atherys.quests.data.QuestData;
import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogTree;
import com.atherys.quests.listeners.EntityListener;
import com.atherys.quests.listeners.GsonListener;
import com.atherys.quests.listeners.InventoryListener;
import com.atherys.quests.listeners.MasterEventListener;
import com.atherys.quests.managers.DialogManager;
import com.atherys.quests.managers.QuestManager;
import com.atherys.quests.managers.QuesterManager;
import com.atherys.quests.quest.Quest;
import com.atherys.quests.quest.objective.DialogObjective;
import com.atherys.quests.quest.objective.KillEntityObjective;
import com.atherys.quests.quest.requirement.*;
import com.atherys.quests.quest.reward.MoneyReward;
import com.atherys.quests.quest.reward.MultiItemReward;
import com.atherys.quests.quest.reward.SingleItemReward;
import com.atherys.quests.util.GsonUtils;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameRegistryEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.world.extent.EntityUniverse;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

import static com.atherys.quests.AtherysQuests.*;

@Plugin( id = ID, name = NAME, description = DESCRIPTION, version = VERSION )
public class AtherysQuests {
    public static final String ID = "atherysquests";
    public static final String NAME = "A'therys Quests";
    public static final String DESCRIPTION = "A quest plugin written for the A'therys Horizons server.";
    public static final String VERSION = "1.0.0b";

    @Inject
    PluginContainer container;

    private static AtherysQuests instance;
    private static boolean init = false;
    private static QuestsConfig config;

    @Inject
    Logger logger;

    private void init() {
        // TODO: Dump assets into config file
        // TODO: Load dialogs from files
        instance = this;

        try {
            config = new QuestsConfig( "config/" + ID, "config.conf" );
            config.init();
        } catch ( IOException e ) {
            init = false;
            e.printStackTrace();
            return;
        }

        if ( config.IS_DEFAULT ) {
            logger.error( "The AtherysQuests config is set to default. Please change default config settings and then set 'isDefault' to 'false'." );
            init = false;
            return;
        }

        init = true;
    }

    private void start() {

        Sponge.getEventManager().registerListeners( this, new GsonListener() );
        Sponge.getEventManager().registerListeners( this, new EntityListener() );
        Sponge.getEventManager().registerListeners( this, new InventoryListener() );
        Sponge.getEventManager().registerListeners( this, new MasterEventListener() );

        GsonUtils.getRequirementRuntimeTypeAdapterFactory()
                .registerSubtype( AndRequirement.class )
                .registerSubtype( OrRequirement.class )
                .registerSubtype( LevelRequirement.class )
                .registerSubtype( MoneyRequirement.class )
                .registerSubtype( QuestRequirement.class );

        GsonUtils.getObjectiveTypeAdapterFactory()
                .registerSubtype( KillEntityObjective.class )
                .registerSubtype( DialogObjective.class );

        GsonUtils.getRewardRuntimeTypeAdapterFactory()
                .registerSubtype( MoneyReward.class )
                .registerSubtype( MultiItemReward.class )
                .registerSubtype( SingleItemReward.class );

        Quest dummyQuest = Quest.builder( "dummyQuest", 1 )
                .name( Text.of( "This is a dummy quest." ) )
                .description( Text.of( "The purpose of this quest is to demonstrate that quests work. So uhh.. kill 3 unnamed creepers and 4 unnamed zombies. Also speak to the king at the end there. You'll get a magical anvil at the end for it." ) )
                //.add( new OrRequirement(
                //        new LevelRequirement( 10 ),
                //        new AndRequirement(
                //                new OrRequirement(
                //                        new QuestRequirement( "someQuest" ),
                //                        new MoneyRequirement( 10, getEconomyService().get().getDefaultCurrency() )
                //                ),
                //                new QuestRequirement( "someOtherQuest" )
                //        )
                //) )
                .add( KillEntityObjective.of( "creeper", 3 ) )
                .add( KillEntityObjective.of( "zombie", 4 ) )
                //.add( new DialogObjective( "theKingSpeech", 14, Text.of( "Speak to the king." ) ) )
                .add( new SingleItemReward( ItemStack.builder().itemType( ItemTypes.ANVIL ).quantity( 1 ).add( Keys.DISPLAY_NAME, Text.of( "The Magical Anvil" ) ).build() ) )
                .build();

        DialogNode root = DialogNode.builder( 0 )
                .npc( Text.of( "Hello, weary traveller!" ) )
                .responses(
                        DialogNode.builder( 1 )
                                .player( Text.of( "Hello, Merchant! Have you any work for me today?" ) )
                                .npc(
                                        Text.of( "You know, I ", TextStyles.ITALIC, " may ", TextStyles.RESET ,"actually have something you'd be", TextColors.DARK_BLUE, " interested in." ),
                                        Text.of( "Recently, the outskirts of the town have been getting ravaged by some very nasty creatures." ),
                                        Text.of( "I've heard that the King himself wishes this situation to be dealt with swiftly. There may even be a reward for the one who does it." )
                                )
                                .responses(
                                        DialogNode.builder( 10 )
                                                .player( Text.of( "Well, sign me up! I love me a bit of danger." ) )
                                                .npc( Text.of( "Oh, excellent! Just remember who tipped you off, when the time comes for the King to reward you, eh? ;)" ) )
                                                .quest( dummyQuest )
                                                .build(),
                                        DialogNode.builder( 11 )
                                                .player( Text.of( "Oh, no, I'm not in the murdering vibe today. Perhaps another day." ) )
                                                .npc( Text.of( "Well, I wouldn't take too long if I were you. Someone else is bound to pick up on that reward." ) )
                                                .build()
                                )
                                .build(),
                        DialogNode.builder( 2 )
                                .player( Text.of( "I have no time for chit-chat. Goodbye." ) )
                                .build()
                )
                .build();

        DialogTree tree = DialogTree.builder( "merchantDialog" ).root( root ).build();

        DialogManager.getInstance().registerDialog( tree );
        QuestManager.getInstance().registerQuest( dummyQuest );

        QuesterManager.getInstance().loadAll();

        Sponge.getCommandManager().register( this, CommandSpec.builder()
                .executor( ( src, args ) -> {
                    Player player = (Player) src;

                    Iterator<EntityUniverse.EntityHit> targetIter = player.getWorld().getIntersectingEntities( player, 100 ).iterator();
                    if ( targetIter.hasNext() ) {
                        EntityUniverse.EntityHit hit = targetIter.next();

                        player.sendMessage( Text.of( DialogManager.getInstance().setDialog( hit.getEntity(), DialogManager.getInstance().getDialogFromId( args.<String>getOne( "dialogId" ).get() ).get() ) ) );
                    }

                    return CommandResult.empty();
                } )
                .arguments(
                        GenericArguments.string( Text.of("dialogId") )
                )
                .build(), "dialogTest"
        );

        Sponge.getCommandManager().register( this, CommandSpec.builder()
                .executor( ( src, args ) -> {
                    Player player = (Player) src;

                    QuesterManager.getInstance().getQuester( player ).getLog().show( player );

                    return CommandResult.empty();
                })
                .build(), "getquests"
        );

    }

    private void stop() {
        QuesterManager.getInstance().saveAll();
    }

    @Listener
    public void onKeyRegistration( GameRegistryEvent.Register<Key<?>> event ) {
        QuestKeys.DIALOG = Key.builder()
                .type( new TypeToken<Value<String>>() {} )
                .id( "atherysquests:dialog" )
                .name( "Dialog" )
                .query( DataQuery.of( "Dialog" ) )
                .build();

        QuestKeys.QUEST = Key.builder()
                .type( new TypeToken<Value<String>>() {} )
                .id( "atherysquests:quest" )
                .name( "Quest" )
                .query( DataQuery.of( "Quest" ) )
                .build();
    }

    @Listener
    public void onDataRegistration ( GameRegistryEvent.Register<DataRegistration<?,?>> event ) {

        QuestKeys.DIALOG_DATA_REGISTRATION = DataRegistration.builder()
                .dataClass( DialogData.class )
                .immutableClass( DialogData.Immutable.class )
                .builder( new DialogData.Builder() )
                .dataName( "Dialog" )
                .manipulatorId( "dialog" )
                .buildAndRegister( this.container );

        QuestKeys.QUEST_DATA_REGISTRATION = DataRegistration.builder()
                .dataClass( QuestData.class )
                .immutableClass( QuestData.Immutable.class )
                .builder( new QuestData.Builder() )
                .dataName( "Quest" )
                .manipulatorId( "quest" )
                .buildAndRegister( this.container );
    }

    @Listener
    public void onInit( GameInitializationEvent event ) {
        init();
    }

    @Listener
    public void onStart( GameStartedServerEvent event ) {
        if ( init ) start();
    }

    @Listener
    public void onStop( GameStoppingServerEvent event ) {
        if ( init ) stop();
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

    public Logger getLogger() {
        return logger;
    }
}
