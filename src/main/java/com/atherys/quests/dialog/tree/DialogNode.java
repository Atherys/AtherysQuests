package com.atherys.quests.dialog.tree;

import com.atherys.quests.dialog.requirements.DialogRequirement;
import com.atherys.quests.quest.Quest;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DialogNode {

    private int id;
    private List<DialogRequirement> requirements = new ArrayList<>();

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

    public List<DialogRequirement> getRequirements() {
        return requirements;
    }

    public boolean meetsRequirements ( Player player ) {
        for ( DialogRequirement requirement : requirements ) {
            if ( !requirement.check( player ) ) return false;
        }
        return true;
    }

    public Optional<Quest> getQuest() {
        return Optional.ofNullable( quest );
    }
}
