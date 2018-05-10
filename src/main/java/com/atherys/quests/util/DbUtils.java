package com.atherys.quests.util;

import com.flowpowered.math.vector.Vector3d;
import com.google.gson.Gson;
import org.bson.Document;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;
import java.util.UUID;

public class DbUtils {
    private static Gson gson = new Gson();

    public static class Serialize {

        public static Document location(Location<World> location) {
            Vector3d position = location.getPosition();
            return new Document()
                    .append("world", location.getExtent().getUniqueId().toString())
                    .append("x", position.getX())
                    .append("y", position.getY())
                    .append("z", position.getZ());
        }
    }

    public static class Deserialize {

        public static Optional<Location<World>> location(Document doc) {
            Vector3d position = new Vector3d(
                    doc.getDouble("x"),
                    doc.getDouble("y"),
                    doc.getDouble("z")
            );
            Optional<World> worldOpt = Sponge.getServer()
                    .getWorld(UUID.fromString(doc.getString("world")));
            return worldOpt.map(world1 -> new Location<>(world1, position));
        }
    }
}
