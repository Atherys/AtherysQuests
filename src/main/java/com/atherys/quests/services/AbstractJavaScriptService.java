package com.atherys.quests.services;

import com.atherys.quests.api.script.Script;
import com.atherys.quests.api.script.ScriptService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractJavaScriptService<T extends Script> implements ScriptService<T> {

    private Map<String, T> scripts = new HashMap<>();

    @Override
    public void loadAllFromFolder(String path) throws IOException {
        File folder = new File(path);

        if (!folder.isDirectory()) return;

        File[] files = folder.listFiles();

        if (files == null) {
            throw new FileNotFoundException("Could not list files in provided directory.");
        } else {
            for (File file : files) {
                if (!file.getName().endsWith(".js")) continue;
                T script = fromFile(file);
                registerScript(script);
            }
        }
    }

    @Override
    public void registerScript(T script) {
        this.scripts.put(script.getId(), script);
    }

    @Override
    public Optional<T> getScriptById(String id) {
        return Optional.ofNullable(scripts.get(id));
    }

    @Override
    public Collection<T> getScripts() {
        return scripts.values();
    }
}
