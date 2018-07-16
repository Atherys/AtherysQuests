package com.atherys.quests.event;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.dialog.tree.DialogTree;
import com.atherys.quests.service.DialogService;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

/**
 * An event for registering dialogs with the {@link DialogService}. This event is called immediately after {@link QuestRegistrationEvent}.
 */
public class DialogRegistrationEvent implements Event {

    private Cause cause;

    public DialogRegistrationEvent(DialogService manager) {
        this.cause = Cause.builder().append(AtherysQuests.getInstance()).append(manager).build(Sponge.getCauseStackManager().getCurrentContext());
    }

    @Override
    public Cause getCause() {
        return cause;
    }

    public DialogService getManager() {
        return DialogService.getInstance();
    }

    public void register(DialogTree tree) {
        getManager().registerDialog(tree);
    }
}
