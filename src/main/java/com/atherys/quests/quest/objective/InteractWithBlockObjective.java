package com.atherys.quests.quest.objective;

import com.atherys.quests.api.objective.AbstractObjective;
import com.atherys.quests.quester.Quester;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import java.util.Collections;
import java.util.List;

/**
 * And objective for interacting with a {@link BlockSnapshot}
 */
public class InteractWithBlockObjective extends AbstractObjective<InteractBlockEvent> {

    @Expose
    private BlockSnapshot snapshot;
    @Expose
    private List<ItemStack> allowedItems;
    @Expose
    private boolean complete = false;

    private InteractWithBlockObjective() {
        super(InteractBlockEvent.class);
    }

    InteractWithBlockObjective(BlockSnapshot snapshot, List<ItemStack> items) {
        this();
        this.snapshot = snapshot;
        this.allowedItems = items;
    }

    InteractWithBlockObjective(BlockSnapshot snapshot) {
        this();
        this.snapshot = snapshot;
        this.allowedItems = Collections.emptyList();
    }

    @Override
    protected void onNotify(InteractBlockEvent event, Quester quester) {
        if (event.getTargetBlock().equals(this.snapshot)) {
            if (this.allowedItems.isEmpty()) {
                this.complete = true;
            } else {
                ItemStack item = quester.getCachedPlayer().getItemInHand(HandTypes.MAIN_HAND).orElse(ItemStack.empty());
                allowedItems.forEach(itemStack-> {
                    if (itemStack.equalTo(item)) {
                        this.complete = true;
                    }
                });
            }
        }
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public InteractWithBlockObjective copy() {
        return new InteractWithBlockObjective(snapshot);
    }

    @Override
    public Text toText() {
        return Text.builder().append(Text.of("Interact With ", snapshot.getState().getType().getName())).onHover(TextActions.showText(Text.of("Located at ", snapshot.getPosition()))).build();
    }
}
