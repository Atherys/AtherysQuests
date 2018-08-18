package com.atherys.quests.script.lib.test;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.function.Function;

public class TextOf implements Function<String, Text> {
    @Override
    public Text apply(String serializedText) {
        return TextSerializers.FORMATTING_CODE.deserialize(serializedText);
    }
}
