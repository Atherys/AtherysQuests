package com.atherys.quests.quest.objective;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * A factory class for accessing the default Objectives
 */
public final class Objectives {

    public static DialogObjective dialog(String treeId, int node, @Nullable Text description) {
        return new DialogObjective(treeId, node, description);
    }

    public static InteractWithBlockObjective blockInteract(BlockSnapshot snapshot) {
        return new InteractWithBlockObjective(snapshot);
    }

    public static KillEntityObjective killEntity(String entityName, int amount) {
        return new KillEntityObjective(entityName, amount);
    }

    public static ReachLocationObjective reachLocation(Text name, Location<World> location, double radius) {
        return new ReachLocationObjective(name, location, radius);
    }

    public static ItemDeliveryObjective itemDelivery(ItemStackSnapshot item, UUID entity, Text entityName) {
        return new ItemDeliveryObjective(item, entity, entityName);
    }

}
