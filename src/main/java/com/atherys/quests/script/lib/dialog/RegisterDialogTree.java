package com.atherys.quests.script.lib.dialog;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.dialog.tree.DialogTree;
import com.atherys.script.api.function.ScriptFunction;
import org.spongepowered.api.scheduler.Task;

import java.util.concurrent.TimeUnit;

/**
 * @jsfunc
 */
public class RegisterDialogTree implements ScriptFunction<DialogTree, Boolean> {
    /**
     * Registers a dialog tree for use.
     */
    @Override
    public Boolean call(DialogTree dialogTree) {
        Task.builder()
                .name("dialog-tree-registration-task-" + dialogTree.getId())
                .delay(5, TimeUnit.SECONDS)
                .execute(() -> AtherysQuests.getInstance().getDialogService().registerDialog(dialogTree))
                .submit(AtherysQuests.getInstance());
        return true;
    }
}
