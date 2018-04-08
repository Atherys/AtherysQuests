package com.atherys.quests;

import com.atherys.quests.data.DialogData;
import com.atherys.quests.data.QuestData;
import com.atherys.quests.events.DialogRegistrationEvent;
import com.atherys.quests.events.QuestRegistrationEvent;
import com.atherys.quests.listeners.*;
import com.atherys.quests.managers.DialogManager;
import com.atherys.quests.managers.QuesterManager;
import com.atherys.quests.quest.DeliverableSimpleQuest;
import com.atherys.quests.quest.DeliverableStagedQuest;
import com.atherys.quests.quest.SimpleQuest;
import com.atherys.quests.quest.StagedQuest;
import com.atherys.quests.quest.objective.DialogObjective;
import com.atherys.quests.quest.objective.InteractWithBlockObjective;
import com.atherys.quests.quest.objective.KillEntityObjective;
import com.atherys.quests.quest.objective.ReachLocationObjective;
import com.atherys.quests.quest.requirement.*;
import com.atherys.quests.quest.reward.MoneyReward;
import com.atherys.quests.quest.reward.SingleItemReward;
import com.atherys.quests.util.GsonUtils;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.extent.EntityUniverse;

import java.io.IOException;
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

    private void init () {
        instance = this;

        try {
            config = new QuestsConfig( getWorkingDirectory(), "config.conf" );
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

    private void start () {

        Sponge.getEventManager().registerListeners( this, new GsonListener() );
        Sponge.getEventManager().registerListeners( this, new EntityListener() );
        Sponge.getEventManager().registerListeners( this, new InventoryListener() );
        Sponge.getEventManager().registerListeners( this, new MasterEventListener() );
        //Sponge.getEventManager().registerListeners( this, new DialogQuestRegistrationListener() );

        GsonUtils.getQuestRuntimeTypeAdapterFactory()
                .registerSubtype( SimpleQuest.class )
                .registerSubtype( StagedQuest.class )
                .registerSubtype( DeliverableSimpleQuest.class )
                .registerSubtype( DeliverableStagedQuest.class );

        GsonUtils.getRequirementRuntimeTypeAdapterFactory()
                .registerSubtype( AndRequirement.class )
                .registerSubtype( OrRequirement.class )
                .registerSubtype( NotRequirement.class )
                .registerSubtype( LevelRequirement.class )
                .registerSubtype( MoneyRequirement.class )
                .registerSubtype( QuestRequirement.class );

        GsonUtils.getObjectiveTypeAdapterFactory()
                .registerSubtype( KillEntityObjective.class )
                .registerSubtype( DialogObjective.class )
                .registerSubtype( ReachLocationObjective.class )
                .registerSubtype( InteractWithBlockObjective.class );

        GsonUtils.getRewardRuntimeTypeAdapterFactory()
                .registerSubtype( MoneyReward.class )
                .registerSubtype( SingleItemReward.class );

        QuestRegistrationEvent questRegistrationEvent = new QuestRegistrationEvent();
        Sponge.getEventManager().post( questRegistrationEvent );

        DialogRegistrationEvent dialogRegistrationEvent = new DialogRegistrationEvent();
        Sponge.getEventManager().post( dialogRegistrationEvent );

        QuesterManager.getInstance().loadAll();

        Sponge.getCommandManager().register( this, CommandSpec.builder()
                .executor( ( src, args ) -> {
                    Player player = (Player) src;

                    for ( EntityUniverse.EntityHit entityHit : player.getWorld().getIntersectingEntities( player, 100 ) ) {
                        Entity next = entityHit.getEntity();
                        if ( next instanceof Player ) continue;

                        player.sendMessage( Text.of( DialogManager.getInstance().setDialog( entityHit.getEntity(), DialogManager.getInstance().getDialogFromId( args.<String>getOne( "dialogId" ).get() ).get() ) ) );
                    }

                    return CommandResult.empty();
                } )
                .arguments(
                        GenericArguments.string( Text.of( "dialogId" ) )
                )
                .build(), "setdialog"
        );

        Sponge.getCommandManager().register( this, CommandSpec.builder()
                .executor( ( src, args ) -> {
                    Player player = (Player) src;

                    QuesterManager.getInstance().getQuester( player ).getLog().show( player );

                    return CommandResult.empty();
                } )
                .build(), "getquests"
        );

        Sponge.getCommandManager().register( this, CommandSpec.builder()
                .executor( ( src, args ) -> {
                    Player player = (Player) src;

                    for ( EntityUniverse.EntityHit entityHit : player.getWorld().getIntersectingEntities( player, 100 ) ) {
                        Entity next = entityHit.getEntity();
                        if ( next instanceof Player ) continue;

                        player.sendMessage( Text.of( "Dialog Test: ", DialogManager.getInstance().getDialog( entityHit.getEntity() ).isPresent() ) );
                    }

                    return CommandResult.empty();
                } )
                .build(), "testdialog"
        );

    }

    private void stop () {
        QuesterManager.getInstance().saveAll();
    }

    @Listener
    public void preInit ( GamePreInitializationEvent event ) {
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
    public void onInit ( GameInitializationEvent event ) {
        init();
    }

    @Listener
    public void onStart ( GameStartedServerEvent event ) {
        if ( init ) start();
    }

    @Listener
    public void onStop ( GameStoppingServerEvent event ) {
        if ( init ) stop();
    }

    public String getWorkingDirectory () {
        return "config/" + ID;
    }

    public static AtherysQuests getInstance () {
        return instance;
    }

    public static QuestsConfig getConfig () {
        return config;
    }

    public Optional<EconomyService> getEconomyService () {
        return Sponge.getServiceManager().provide( EconomyService.class );
    }

    public Logger getLogger () {
        return logger;
    }
}
