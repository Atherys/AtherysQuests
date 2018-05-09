package com.atherys.quests.dialog.tree;

public class DialogTreeBuilder {

    String treeId;
    DialogNode root;

    public DialogTreeBuilder(String id) {
        this.treeId = id;
    }

    public DialogTreeBuilder root(DialogNode node) {
        this.root = node;
        return this;
    }

    public DialogTree build() {
        if(root == null) {
            throw new IllegalStateException("DialogTree \"" + treeId + "\" contains a null root. Please provide the root for this DialogTree.");
        }
        return new DialogTree(treeId, root);
    }

}
