package com.atherys.quests.persistence.converter;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.google.gson.Gson;

import javax.persistence.AttributeConverter;

public class QuestConverter implements AttributeConverter<Quest,String> {

    private Gson gson = AtherysQuests.getInstance().getAtherysQuestsRegistry().getGson();

    @Override
    public String convertToDatabaseColumn(Quest attribute) {
        return gson.toJson(attribute);
    }

    @Override
    public Quest convertToEntityAttribute(String dbData) {
        return gson.fromJson(dbData, Quest.class);
    }
}
