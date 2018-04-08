package com.atherys.quests.dialog.tree;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;
import org.spongepowered.api.text.Text;

public class DialogNodeBuilder {

    DialogNode node;

    public DialogNodeBuilder() {
        node = new DialogNode(-1);
    }

    public DialogNodeBuilder( int id ) {
        node = new DialogNode( id );
    }

    public DialogNodeBuilder id ( int id ) {
        node.setId( id );
        return this;
    }

    public DialogNodeBuilder player ( Text text ) {
        this.node.setPlayerText( text );
        return this;
    }

    public DialogNodeBuilder npc ( Text... text ) {
        this.node.setNPCText( text );
        return this;
    }

    public DialogNodeBuilder quest ( Quest quest ) {
        this.node.setQuest ( quest.getId() );
        return this;
    }

    public DialogNodeBuilder requirements ( Requirement... requirements ) {
        this.node.setRequirements( requirements );
        return this;
    }

    public DialogNodeBuilder responses ( DialogNode... nodes ) {
        this.node.setResponses( nodes );
        return this;
    }

    public DialogNode build() {
        if ( node.getId() == -1 ) throw new IllegalStateException( "DialogNode requires a valid int id. 0 is preferred for root nodes." );
        if ( node.getId() != 0 && node.getPlayerText() == null ) throw new IllegalStateException( "DialogNode cannot have an empty player text unless it is a root node ( it's id = 0 )." );
        if ( node.getResponses().size() != 0 && node.getNPCText() == null ) throw new IllegalStateException( "DialogNode cannot have empty NPC text unless it is a leaf node." );
        return node;
    }

}
