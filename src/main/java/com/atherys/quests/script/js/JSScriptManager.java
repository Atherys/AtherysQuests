package com.atherys.quests.script.js;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.managers.ScriptManager;
import com.atherys.quests.api.quest.Quest;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JSScriptManager implements ScriptManager {

    private final static JSScriptManager instance = new JSScriptManager();

    private Map<String, JSScript> scripts = new HashMap<>();

    private JSScriptManager() {
        String folder = AtherysQuests.getInstance().getWorkingDirectory() + "/" + AtherysQuests.getConfig().SCRIPTS_FOLDER;

        try {
            loadScripts(new File(folder));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSScriptManager getInstance() {
        return instance;
    }

    /**
     * Load all script LUA files within the given folder.
     * A script file's name ( ex. "scriptFile.js" ) will reflect which quest will use it.
     * A script file's name should be identical to the quest id in order for it to be used for the relevant quest.
     *
     * @param folder The File representing the directory.
     * @throws FileNotFoundException If the file could not be found
     */
    public void loadScripts(@Nonnull File folder) throws IOException {
        if (!folder.isDirectory()) return;

        File[] files = folder.listFiles();

        if (files == null) {
            throw new FileNotFoundException("Could not list files in provided directory.");
        } else {
            for (File file : files) {
                if (!file.getName().endsWith(".js")) continue;

                JSScript script = JSScript.fromFile(file);
                registerScript(script);
            }
        }
    }

    public void registerScript(JSScript script) {
        this.scripts.put(script.getId(), script);
    }

    @Override
    public Optional<JSScript> forQuest(Quest quest) {
        return Optional.ofNullable(scripts.get(quest.getId()));
    }
}
