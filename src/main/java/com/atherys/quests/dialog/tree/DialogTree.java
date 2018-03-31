package com.atherys.quests.dialog.tree;

import com.google.gson.annotations.Expose;

public class DialogTree {

    @Expose private String id;
    @Expose private DialogNode root;

    protected DialogTree( String id, DialogNode root ) {
        this.id = id;
        this.root = root;
    }

    public static DialogTreeBuilder builder( String id ) {
        return new DialogTreeBuilder( id );
    }

    public String getId() {
        return id;
    }

    public DialogNode getRoot() {
        return root;
    }

    public void setId( String id ) {
        this.id = id;
    }
}
