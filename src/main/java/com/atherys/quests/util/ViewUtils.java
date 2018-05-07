package com.atherys.quests.util;

import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.Map;

public final class ViewUtils {

    public static Text mapToText(Text name, Map<?, ?> map) {
        Text.Builder text = name.toBuilder();
        map.forEach((k, v) -> text.append(Text.of(k, ": ", v)));
        return text.build();
    }

    public static Text listToText(Text name, List<?> list) {
        Text.Builder text = name.toBuilder();
        list.forEach(item -> text.append(Text.of(item)));
        return text.build();
    }

    public static BookView bookFromText(Text text) {
        return BookView.builder().addPage(text).build();
    }

}
