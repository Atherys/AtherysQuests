package com.atherys.quests.gson;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.quest.DeliverableQuest;
import com.google.gson.*;
import org.spongepowered.api.text.Text;

import java.lang.reflect.Type;
import java.util.UUID;

public class DeliverableQuestAdapter implements JsonDeserializer<DeliverableQuest> {
    private JsonParser parser = new JsonParser();

    @Override
    public DeliverableQuest deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject()) {
            Gson gson = AtherysQuests.getInstance().getGson();
            JsonObject object = json.getAsJsonObject();
            Quest quest = gson.fromJson(json.getAsJsonObject().get("quest"), Quest.class);
            UUID target = gson.fromJson(object.get("target"), UUID.class);
            Text targetName = gson.fromJson(object.get("targetName"), Text.class);
            return new DeliverableQuest<>(quest, target, targetName);
        }
        return null;
    }
}
