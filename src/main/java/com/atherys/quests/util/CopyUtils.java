package com.atherys.quests.util;

import com.atherys.quests.base.Prototype;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class CopyUtils {

    @SuppressWarnings( "unchecked" )
    public static <T extends Prototype> List<T> copyList( List<T> original ) {
        List<T> copyList = new ArrayList<>( original.size() );
        for ( T object : original ) {
            copyList.add( ( T ) object.copy() );
        }
        return copyList;
    }

    public static List<ItemStack> copyItemStackList( List<ItemStack> original ) {
        List<ItemStack> copy = new ArrayList<>( original.size() );
        for ( ItemStack stack : original ) {
            copy.add( stack.copy() );
        }
        return copy;
    }

}
