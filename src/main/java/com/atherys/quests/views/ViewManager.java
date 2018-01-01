package com.atherys.quests.views;

import com.atherys.quests.base.Viewable;
import com.atherys.quests.views.api.View;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ViewManager {

    private static ViewManager instance = new ViewManager();

    private Map<Class<? extends Viewable>, Class<? extends View>> viewClasses = new HashMap<>();

    private ViewManager() {

    }

    public void registerView ( Class<? extends Viewable> clazz, Class<? extends View> viewClass ) {
        viewClasses.put( clazz, viewClass );
    }

    public <T extends Viewable> Optional<View> getView ( T object ) {
        if ( !viewClasses.containsKey( object.getClass() ) ) return Optional.empty();

        try {
            return Optional.of( viewClasses.get( object.getClass() ).getConstructor( object.getClass() ).newInstance( object ) );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public static ViewManager getInstance() {
        return instance;
    }

}
