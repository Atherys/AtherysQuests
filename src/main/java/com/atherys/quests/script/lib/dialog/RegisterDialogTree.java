package com.atherys.quests.script.lib.dialog;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.dialog.tree.DialogTree;
import org.spongepowered.api.scheduler.Task;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @jsfunc
 */ 
public class RegisterDialogTree implements Function<DialogTree,Boolean> {
    @Override
    public Boolean apply(DialogTree dialogTree) {
        Task.builder()
                .name("dialog-tree-registration-task-" + dialogTree.getId())
                .delay(5, TimeUnit.SECONDS)
                .execute(() -> AtherysQuests.getDialogService().registerDialog(dialogTree))
                .submit(AtherysQuests.getInstance());
        return true;
    }
}
