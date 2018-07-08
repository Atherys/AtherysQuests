package com.atherys.quests.dialog.tree;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.script.DialogScript;
import com.atherys.quests.api.script.Script;
import com.google.gson.annotations.Expose;

import java.util.Optional;

public class DialogTree {
    @Expose
    private String id;

    @Expose
    private DialogNode root;

    @Expose
    private String dialogId;

    protected DialogTree(String id, DialogNode root) {
        this.id = id;
        this.root = root;
    }

    public static DialogTreeBuilder builder(String id) {
        return new DialogTreeBuilder(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DialogNode getRoot() {
        return root;
    }

    public Optional<DialogScript> getScript() {
        return AtherysQuests.getScriptService().getScriptById(dialogId);
    }
}
