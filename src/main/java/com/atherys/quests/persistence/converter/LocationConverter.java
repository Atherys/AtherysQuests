package com.atherys.quests.persistence.converter;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.persistence.AttributeConverter;

public class LocationConverter implements AttributeConverter<Location<World>,String> {
    @Override
    public String convertToDatabaseColumn(Location<World> attribute) {
        return "";
    }

    @Override
    public Location<World> convertToEntityAttribute(String dbData) {
        return null;
    }
}
