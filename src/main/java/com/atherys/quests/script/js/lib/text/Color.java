package com.atherys.quests.script.js.lib.text;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

import java.util.function.Function;

public class Color implements Function<String,TextColor> {

    Color() {}

    @Override
    public TextColor apply(String textColorId) {
        return Sponge.getRegistry().getType(TextColor.class, textColorId).orElse(TextColors.NONE);
    }
}
