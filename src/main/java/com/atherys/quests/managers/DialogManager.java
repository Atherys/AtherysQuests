package com.atherys.quests.managers;

import com.atherys.quests.QuestKeys;
import com.atherys.quests.data.DialogData;
import com.atherys.quests.dialog.Dialog;
import com.atherys.quests.dialog.tree.DialogTree;
import com.google.gson.Gson;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * A class responsible for managing all dialogs
 */
public final class DialogManager {

    private static DialogManager instance = new DialogManager();

    private Map<UUID, Dialog> ongoingDialogs = new HashMap<>();
    private Map<String, DialogTree> trees = new HashMap<>();

    private Gson gson = new Gson();

    private DialogManager() {
        // TODO: Init Gson with necessary type adapters
        // TODO: Write necessary Gson type adapters ( DialogTree, DialogNode, Requirement )
    }

    /**
     * Load all dialog JSON files within the given folder.
     *
     * @param folder The File representing the directory.
     * @throws FileNotFoundException If the file could not be found
     */
    public void loadDialogs( @Nonnull File folder ) throws FileNotFoundException {
        if ( !folder.isDirectory() ) return;

        File[] files = folder.listFiles();

        if ( files == null ) {
            throw new FileNotFoundException( "Could not list files in provided directory." );
        } else {
            for ( File file : files ) {
                if ( !file.getName().endsWith( ".json" ) ) continue;

                DialogTree tree = gson.fromJson( new FileReader( file ), DialogTree.class );
                tree.setId( file.getName().replace( ".json", "" ) );
                this.trees.put( tree.getId(), tree );
            }
        }
    }

    public Optional<DialogTree> getDialogFromId( String id ) {
        return Optional.ofNullable( trees.get( id ) );
    }

    /**
     * Checks to see if an {@link Entity} contains a {@link DialogTree}.
     *
     * @param entity The entity to be checked
     * @return Whether or not this entity contains a DialogTree
     */
    public boolean hasDialog( Entity entity ) {
        return entity.get( QuestKeys.DIALOG ).isPresent();
    }

    /**
     * Used to get the {@link DialogTree} an entity is associated with.
     *
     * @param entity
     * @return An optional containing the dialog this entity is associated with. The optional is empty if the entity does not contain a dialog.
     */
    public Optional<DialogTree> getDialog( Entity entity ) {
        Optional<String> dialogId = entity.get( QuestKeys.DIALOG );
        if ( dialogId.isPresent() ) {
            return Optional.ofNullable( trees.get( dialogId.get() ) );
        } else return Optional.empty();
    }

    /**
     * Associate an entity with a {@link DialogTree}. When a player attempts to interact with this entity, they will begin the dialog.
     *
     * @param entity The entity to be assigned a dialog
     * @param tree   The dialog tree itself
     * @return Whether or not setting the dialog was successful.
     */
    public boolean setDialog( Entity entity, DialogTree tree ) {
        return entity.offer( new DialogData( tree.getId() ) ).isSuccessful();
    }

    /**
     * Used to check if a player is currently in a dialog with an NPC or not.
     *
     * @param player The player to check
     * @return Whether or not the player is participating in a dialog
     */
    public boolean hasPlayerDialog( Player player ) {
        return ongoingDialogs.containsKey( player.getUniqueId() );
    }

    /**
     * Used to get the dialog the player is currently participating in.
     *
     * @param player The player whose dialog is to be gotten
     * @return An optional containing the dialog. Is empty if no dialog is found.
     */
    public Optional<Dialog> getPlayerDialog( Player player ) {
        return Optional.ofNullable( ongoingDialogs.get( player.getUniqueId() ) );
    }

    /**
     * Begin a dialog between a player and an entity.
     * This will check if the player is currently in another dialog. If so, the dialog will not be started.
     * This will also check if the entity has a dialog. If not, a dialog will not be started.
     *
     * @param player The player participant
     * @param entity The entity participant
     * @return An optional containing the dialog. Empty if failure.
     */
    public Optional<Dialog> startDialog( Player player, Entity entity ) {
        Optional<DialogTree> tree = getDialog( entity );

        if ( !tree.isPresent() || hasPlayerDialog( player ) ) return Optional.empty();

        Optional<Dialog> dialog = Dialog.between( player, entity, tree.get() );
        if ( !dialog.isPresent() ) return Optional.empty();

        this.ongoingDialogs.put( player.getUniqueId(), dialog.get() );
        return dialog;
    }

    public static DialogManager getInstance() {
        return instance;
    }
}
