package com.atherys.quests.script.lib.dialog;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogNodeBuilder;
import com.atherys.quests.util.HexaFunction;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.function.Function;

/**
 * @jsfunc
 */
public class CreateDialogNode implements HexaFunction<Integer, List<Requirement>, Text, List<Text>, Quest, List<DialogNode>, DialogNode> {

    /**
     * Creates a new dialog node. See
     * [Writing a Dialog](https://atherys.com/docs/quests/Writing-a-Dialog.html) for how to use it.
     *
     * @param id           The numerical id of the node ( must be unique within the Dialog Tree )
     * @param requirements The list of requirements the player must fulfill in order to select this as a response ( can be null )
     * @param player       The text that the player will say to the npc ( if this is the root node, this should be null )
     * @param npc          the npc's text response
     * @param quest        the quest that will be offered to the player upon selecting this node ( can be null )
     * @param responses    The responses that the player can select ( Can be null. Should be null if this is a leaf node )
     * @return A new DialogNode object
     */
    @Override
    public DialogNode apply(
            Integer id,
            List<Requirement> requirements,
            Text player,
            List<Text> npc,
            Quest quest,
            List<DialogNode> responses
    ) {
        DialogNodeBuilder builder = DialogNode.builder(id);

        if (requirements != null) builder.requirements((Requirement[]) requirements.toArray());

        if (quest != null) builder.quest(quest);

        if (responses != null) builder.responses((DialogNode[]) responses.toArray());

        builder.player(player);
        builder.npc((Text[]) npc.toArray());

        return builder.build();
    }
}
