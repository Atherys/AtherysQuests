package com.atherys.quests.dialog.tree;

import com.atherys.quests.quest.Quest;
import com.atherys.quests.quest.requirement.Requirement;
import com.atherys.quests.quester.Quester;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DialogNode {

    private int id;
    private List<Requirement> requirements = new ArrayList<>();

    private Text playerText;
    private Text[] npcResponse;

    private Quest quest;

    private List<DialogNode> responses;

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

    public boolean meetsRequirements ( Quester player ) {
        for ( Requirement requirement : requirements ) {
            if ( !requirement.check( player ) ) return false;
        }
        return true;
    }

    public Optional<Quest> getQuest() {
        return Optional.ofNullable( quest );
    }
}
