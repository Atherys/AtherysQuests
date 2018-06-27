package com.atherys.quests.script.js.lib.text;

import org.spongepowered.api.text.Text;

import java.util.function.Function;

public class TextOf implements Function<Object[], Text> {

    TextOf() {}

    @Override
    public Text apply(Object[] objects) {
        return Text.of(objects);
    }
}
