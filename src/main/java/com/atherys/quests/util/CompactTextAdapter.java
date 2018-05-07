package com.atherys.quests.util;

import com.google.gson.*;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.lang.reflect.Type;

public final class CompactTextAdapter implements JsonSerializer<Text>, JsonDeserializer<Text> {
    @Override
    public Text deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return TextSerializers.FORMATTING_CODE.deserialize(json.getAsString());
    }

    @Override
    public JsonElement serialize(Text src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(TextSerializers.FORMATTING_CODE.serialize(src));
    }
}
