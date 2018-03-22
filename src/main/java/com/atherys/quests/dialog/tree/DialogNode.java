package com.atherys.quests.dialog.tree;

import com.atherys.quests.managers.QuestManager;
import com.atherys.quests.quest.Quest;
import com.atherys.quests.quest.requirement.Requirement;
import com.atherys.quests.quester.Quester;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DialogNode {

    @Expose private int id;
    @Expose private List<Requirement> requirements = new ArrayList<>();

    @Expose private Text playerText;
    @Expose private Text[] npcResponse;

    @Expose private String questId;

    @Expose private List<DialogNode> responses = new ArrayList<>();

    protected DialogNode( int id ) {
        this.id = id;
    }

    public static DialogNodeBuilder builder() {
        return new DialogNodeBuilder();
    }

    public static DialogNodeBuilder builder ( int id ) {
        return new DialogNodeBuilder( id );
    }

    public List<DialogNode> getResponses() {
        return responses;
    }

    public Text[] getNPCText() {
        return npcResponse;
    }

    public Text getPlayerText() {
        return playerText;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public boolean meetsRequirements( Quester player ) {
        for ( Requirement requirement : requirements ) {
            if ( !requirement.check( player ) ) return false;
        }
        return true;
    }

    public Optional<Quest> getQuest() {
        return QuestManager.getInstance().getQuest( questId );
    }

    public int getId() {
        return id;
    }

    protected void setPlayerText( Text playerText ) {
        this.playerText = playerText;
    }

    protected void setNPCText( Text... NPCText ) {
        this.npcResponse = NPCText;
    }

    protected void setQuest( String quest ) {
        this.questId = quest;
    }

    protected void setRequirements( Requirement... requirements ) {
        this.requirements = Arrays.asList( requirements );
    }

    protected void setResponses( DialogNode... responses ) {
        this.responses = Arrays.asList( responses );
    }

    public void setId( int id ) {
        this.id = id;
    }
}
