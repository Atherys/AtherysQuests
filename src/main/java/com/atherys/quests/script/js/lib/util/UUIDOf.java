package com.atherys.quests.script.js.lib.util;

import java.util.UUID;
import java.util.function.Function;

public class UUIDOf implements Function<String, UUID> {
    @Override
    public UUID apply(String s) {
        return UUID.fromString(s);
    }
}
