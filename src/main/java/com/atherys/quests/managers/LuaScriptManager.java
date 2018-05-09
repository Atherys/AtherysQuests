package com.atherys.quests.managers;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.quest.script.LuaScript;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LuaScriptManager implements com.atherys.quests.api.managers.ScriptManager<LuaScript> {

    private final static LuaScriptManager instance = new LuaScriptManager();

    private Map<String, LuaScript> scripts = new HashMap<>();

    private LuaScriptManager() {
        String folder = AtherysQuests.getInstance().getWorkingDirectory() + "/" + AtherysQuests.getConfig().SCRIPTS_FOLDER;
        // TODO: loadScripts from the above folder
    }

    /**
     * Load all script LUA files within the given folder.
     * A script file's name ( ex. "scriptFile.lua" ) will reflect which quest will use it.
     * A script file's name should be identical to the quest id in order for it to be used for the relevant quest.
     *
     * @param folder The File representing the directory.
     * @throws FileNotFoundException If the file could not be found
     */
    public void loadScripts(@Nonnull File folder) throws FileNotFoundException {
        if(!folder.isDirectory()) return;

        File[] files = folder.listFiles();

        if(files == null) {
            throw new FileNotFoundException("Could not list files in provided directory.");
        } else {
            for(File file : files) {
                if(!file.getName().endsWith(".lua")) continue;

                LuaScript script = LuaScript.fromFile(file);
                registerScript(script);
            }
        }
    }

    public void registerScript(LuaScript script) {
        this.scripts.put(script.getId(), script);
    }

    @Override
    public Optional<LuaScript> forQuest(Quest quest) {
        return Optional.ofNullable(scripts.get(quest.getId()));
    }

    public static LuaScriptManager getInstance() {
        return instance;
    }

}
