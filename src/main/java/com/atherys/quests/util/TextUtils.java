package com.atherys.quests.util;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import javax.annotation.Nonnull;

public final class TextUtils {

    public static String toString(@Nonnull final Text txt) {
        return TextSerializers.formattingCode('&').serialize(txt);
    }

    public static int getLength(Text text) {
        return toString(text).length();
    }

}
