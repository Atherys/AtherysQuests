package com.atherys.quests.util;

import com.atherys.quests.quest.objective.Objective;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

import java.util.ArrayList;
import java.util.List;

public class ObjectiveListTypeSerializer implements TypeSerializer<List<Objective>> {

    @Override
    public List<Objective> deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        List<Objective> objectives = new ArrayList<>();

        value.getChildrenList().forEach(node -> {
            try {
                objectives.add( node.getValue(TypeToken.of(Objective.class)) );
            } catch (ObjectMappingException e) {
                e.printStackTrace();
            }
        });

        return objectives;
    }

    @Override
    public void serialize(TypeToken<?> type, List<Objective> obj, ConfigurationNode value) throws ObjectMappingException {
        obj.forEach(value::setValue);
    }

}
