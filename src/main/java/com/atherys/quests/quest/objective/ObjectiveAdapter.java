package com.atherys.quests.quest.objective;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class ObjectiveAdapter implements TypeSerializer<Objective> {

    @Override
    public Objective deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        String typeName = value.getNode("objectiveType").getString();
        if ( typeName != null ) {
            try {
                Class<?> c = Class.forName(typeName);

                if ( c.isAssignableFrom(Objective.class) ) {
                    ConfigurationNode data = value.getNode("data");
                    return (Objective) data.getValue(c);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void serialize(TypeToken<?> type, Objective obj, ConfigurationNode value) throws ObjectMappingException {
        value.getNode("objectiveType").setValue( obj.getClass().getName() );
        value.getNode("data").setValue(obj);
    }
}
