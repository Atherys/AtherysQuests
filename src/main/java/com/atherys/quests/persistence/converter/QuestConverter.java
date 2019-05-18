package com.atherys.quests.persistence.converter;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.google.gson.Gson;
import com.google.gson.JsonNull;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.function.Supplier;

public class QuestConverter implements AttributeConverter<Quest, String> {

    private Supplier<Gson> getGson = () -> AtherysQuests.getInstance().getGson();

    @Override
    public String convertToDatabaseColumn(Quest attribute) {
        Gson gson = getGson.get();

        if (gson == null) {
            return JsonNull.INSTANCE.getAsString();
        }

        AtherysQuests.getInstance().getLogger().info("Converting String to Quest {}", attribute);
        StringBuilder json = new StringBuilder(gson.toJson(attribute));
        json.insert(1, "\"questType\":" + "\"" + attribute.getClass().getSimpleName() + "\",");
        return json.toString();
    }

    @Override
    public Quest convertToEntityAttribute(String dbData) {
        Gson gson = getGson.get();

        if (gson == null || StringUtils.isEmpty(dbData)) {
            return null;
        }
        AtherysQuests.getInstance().getLogger().info("Converting String to Quest {}", dbData);
        return gson.fromJson(dbData, Quest.class);
    }
}
