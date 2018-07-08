package com.atherys.quests.script;

import com.atherys.quests.api.script.Script;

public abstract class AbstractJSScript implements Script {

    protected String id;

    protected String contents;

    protected AbstractJSScript(String id, String content) {
        this.id = id;
        this.contents = content;
    }

    @Override
    public String getId() {
        return id;
    }
}
