package com.atherys.quests.script.lib.dialog;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.dialog.tree.DialogTree;

import java.util.function.Function;

public class RegisterDialogTree implements Function<DialogTree,Boolean> {
    @Override
    public Boolean apply(DialogTree dialogTree) {
        AtherysQuests.getDialogService().registerDialog(dialogTree);
        return true;
    }
}
