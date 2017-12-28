package com.atherys.quests.base;

import java.util.ArrayList;
import java.util.List;

public final class CopyUtils {

    @SuppressWarnings("unchecked")
    public static <T extends Prototype> List<T> copyList (List<T> original ) {
        List<T> copyList = new ArrayList<>( original.size() );
        for ( T object : original ) {
            copyList.add( (T) object.copy() );
        }
        return copyList;
    }

}
