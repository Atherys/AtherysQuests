package com.atherys.quests.api.script;

import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogTree;
import com.atherys.quests.quester.Quester;
import org.spongepowered.api.entity.Entity;

public interface DialogScript extends Script  {

    void onBegin(Quester quester, Entity entity, DialogTree tree);

    void onProgress(Quester quester, Entity entity, DialogTree tree, DialogNode node);

    void onEnd(Quester quester, Entity entity, DialogTree tree, DialogNode node);

}
