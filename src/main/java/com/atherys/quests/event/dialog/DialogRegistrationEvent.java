package com.atherys.quests.event.dialog;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.dialog.tree.DialogTree;
import com.atherys.quests.event.quest.QuestRegistrationEvent;
import com.atherys.quests.service.DialogService;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

/**
 * An event for registering dialogs with the {@link DialogService}. This event is called immediately after {@link QuestRegistrationEvent}.
 */
public class DialogRegistrationEvent implements Event {

    private Cause cause;

    private DialogService service;

    public DialogRegistrationEvent(DialogService service) {
        this.service = service;
        this.cause = Cause.builder().append(AtherysQuests.getInstance()).append(service).build(Sponge.getCauseStackManager().getCurrentContext());
    }

    @Override
    public Cause getCause() {
        return cause;
    }

    public DialogService getManager() {
        return service;
    }

    public void register(DialogTree tree) {
        getManager().registerDialog(tree);
    }
}
