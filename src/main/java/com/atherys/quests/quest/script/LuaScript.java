package com.atherys.quests.quest.script;

import com.atherys.quests.api.quest.Script;
import com.atherys.quests.quester.Quester;

import java.io.File;

public class LuaScript implements Script {

    private String id;

    private LuaScript () {
    }

    public static LuaScript fromFile ( File file ) {
        // TODO: Implement
        return null;
    }

    @Override
    public String getId () {
        return id;
    }

    @Override
    public void pickUp ( Quester quester ) {
        // TODO: Implement
    }

    @Override
    public void progress ( Quester quester ) {
        // TODO: Implement
    }

    @Override
    public void complete ( Quester quester ) {
        // TODO: Implement
    }

    @Override
    public void turnIn ( Quester quester ) {
        // TODO: Implement
    }

}
