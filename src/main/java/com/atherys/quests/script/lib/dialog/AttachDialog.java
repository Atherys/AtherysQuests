package com.atherys.quests.script.lib.dialog;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.dialog.tree.DialogTree;
import com.atherys.script.api.function.ScriptBiFunction;
import org.spongepowered.api.entity.Entity;

import java.util.Optional;

/**
 * @jsfunc
 */
public class AttachDialog implements ScriptBiFunction<Entity, String, Boolean> {
    /**
     * Attaches a dialog to an `Entity`.
     *
     * @return Whether the attachment was successful.
     */
    @Override
    public Boolean call(Entity entity, String dialogId) {
        Optional<DialogTree> dialogTree = AtherysQuests.getInstance().getDialogService().getDialogFromId(dialogId);
        if (dialogTree.isPresent()) {
            return AtherysQuests.getInstance().getDialogService().setDialog(entity, dialogTree.get());
        }

        return false;
    }
}
